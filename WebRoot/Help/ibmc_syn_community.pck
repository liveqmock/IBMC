create or replace package ibmc_syn_community is

  -- Author  :
  -- Created : 2015-03-03 09:28
  -- Purpose :

  -- 全局游标定义
  TYPE T_CURSOR IS REF CURSOR;
  -- 全局常量声明
  
  --（1）验证房产信息导入数据的有效性
  /***
  **  p_optuserid    操作人id
  **  p_parentid    社区id
  ***/
  procedure update_syscommtempdata(p_optuserid in varchar2,p_parentid in varchar2);

  --（2）将导入临时表中的数据复制到正式表中
  /***
  **  p_optuserid    操作人id
  **  p_parentid    社区id
  ***/
  procedure save_syscommtempdataintoreg(p_optuserid in varchar2,p_parentid in varchar2);

end ibmc_syn_community;
/
create or replace package body ibmc_syn_community is
    --（1）验证房产信息导入数据的有效性
    /***
    **  p_optuserid    操作人id
    **  p_parentid    社区id
    ***/
    procedure update_syscommtempdata(p_optuserid in varchar2,p_parentid in varchar2)is
         r_errstr varchar2(1000);
         r_errflag number(1):=1;     -- 数据异常标识
         r_commtype1 number(1):=1;   -- 独立业主标志
         r_commtype2 number(1):=2;   -- 共有业主标志
         r_roomlev   number(1):=5;   -- 房间层级标识
    begin
      -- 更新commtype=1独立业主的业主ID
      update sys_community_temp t
         set t.ownerid = (select p.id from man_peo p where p.idcard = t.owneridcard and p.name = t.ownername)
      where t.optuserid = p_optuserid
        and t.parentid = p_parentid
        and t.commtype = r_commtype1;

      -- 验证判断commtype=1独立业主的业主ID是否存在系统中
      update sys_community_temp t
       set t.errorflag = r_errflag,
           t.errorstr =
                      (case
                         when (select count(1) from man_peo p where p.idcard = t.owneridcard)>0
                         then '业主的身份证和姓名不符合'
                         else '业主人员数据不存在系统中，请录入业主数据后在重试！'
                      end)
      where t.optuserid = p_optuserid
        and t.parentid = p_parentid
        and t.commtype = r_commtype1
        and t.ownerid is null;

      -- 验证房间名称是否是有 数字+房间 组成的名称
      update sys_community_temp t
         set t.errorflag = r_errflag,
             t.errorstr = '房间名称不符合规范'
      where t.optuserid = p_optuserid
        and exists (select 1 from sys_community_temp t2
        where optuserid = p_optuserid and t2.commlev = r_roomlev and t2.id = t.id
        start with parentid = p_parentid
        connect by prior id = parentid)
        and t.commlev = r_roomlev
        -- 当房间名称存在除'房间'其余的字符串时则返回0,否则返回1
        and nvl2(Translate(replace(t.commname, '房间', ''), '\1234567890','\'),'0','1') = 0;

      commit;    -- 提交事务
    --异常捕获
    exception when others then
      begin
        rollback;
        dbms_output.put_line('验证房产信息数据出错了！');
        dbms_output.put_line(sqlerrm);
      end;
    end update_syscommtempdata;

    --（2）将导入临时表中的数据复制到正式表中
    /***
    **  p_optuserid    操作人id
    **  p_parentid    社区id
    ***/
    procedure save_syscommtempdataintoreg(p_optuserid in varchar2,p_parentid in varchar2) is
         r_commpath  varchar2(200); --房产/房间路径
         r_houseid  number(12);     --记录房产id
         r_tempid   number(12);     --记录每一次生成的sequencesID
         r_temporder number(12);    --排序号
         r_housercommpath varchar2(200); --房产路径
         r_roomcommpath varchar2(200); --房间路径
         r_houeslev number(1):=4;   --房产层面
         r_roomlev  number(1):=5;   --房间层面
         r_usesign  number(1):=1;   --启用标识
         r_village  number(12):=abs(p_parentid);  --获取正式表中房产的社区ID
      begin
        -- 获取房产parentid[房产路径]
        select s.commpath into r_commpath from sys_community s where s.id = r_village;

        -- 获取此次导入到临时表中列表数据
        for d in(
           select
              id, parentid, commdetail, commname, ownerid, commtype, commlev,
              usesign, commorder, remark, commzip, commpath, createdate, telephone,
              updatedate, deletesign, optuserid, errorflag, errorstr, owneridcard, ownername
          from sys_community_temp t
          where t.optuserid = p_optuserid
            start with parentid = p_parentid
            connect by prior id = parentid
       )loop
            select seq_sys_community.nextval into r_tempid from dual;
            -- 当为房产记录时
            if(d.commlev = r_houeslev) then
              r_houseid := r_tempid;
              -- 查询房产的排序号
              select count(1)+1 into r_temporder from sys_community where parentid = r_village and deletesign = 0;
              -- 房产路径
              r_housercommpath :=  r_commpath||'/'||r_tempid;

              -- 新增导入的房产信息到正式表中
              insert into sys_community
                (id, parentid, commdetail, commname, ownerid, commtype, commlev, usesign, commorder, remark, commzip, commpath, createdate, updatedate)
              values
                (r_tempid, r_village, d.commdetail, d.commname, d.ownerid, d.commtype, d.commlev, r_usesign, r_temporder, nvl2(nvl2(d.telephone,'联系电话：'||d.telephone,''),'联系电话：'||d.telephone||'；','')||d.remark, d.commzip, r_housercommpath, sysdate, null);
            else
              -- 查询房间的排序号
              select count(1)+1 into r_temporder from sys_community where parentid = r_houseid and deletesign = 0;
              -- 房间路径
              r_roomcommpath := r_commpath||'/'||r_houseid||'/'||r_tempid;
              -- 新增导入的房间信息到正式表中
              insert into sys_community
                (id, parentid, commdetail, commname, ownerid, commtype, commlev, usesign, commorder, remark, commzip, commpath, createdate, updatedate)
              values
                (r_tempid, r_houseid, d.commdetail, d.commname, d.ownerid, d.commtype, d.commlev, r_usesign, r_temporder, nvl2(nvl2(d.telephone,'联系电话：'||d.telephone,''),'联系电话：'||d.telephone||'；','')||d.remark, d.commzip, r_roomcommpath, sysdate, null);
            end if;
       end loop;
       -- 删除此次导入到临时表中的数据
       delete from sys_community_temp t
        where t.optuserid = p_optuserid
          and exists
              (select 1 from sys_community_temp t2
              where t2.optuserid = p_optuserid
              start with t2.parentid = p_parentid
              connect by prior t2.id = t2.parentid
              );
    --异常捕获
    exception when others then
      begin
        rollback;
        dbms_output.put_line('从临时表中复制到正式表中数据出错了！');
        dbms_output.put_line(sqlerrm);
      end;
    end save_syscommtempdataintoreg;

end ibmc_syn_community;
/
