create or replace package ibmc_syn_community is

  -- Author  :
  -- Created : 2015-03-03 09:28
  -- Purpose :

  -- ȫ���α궨��
  TYPE T_CURSOR IS REF CURSOR;
  -- ȫ�ֳ�������
  
  --��1����֤������Ϣ�������ݵ���Ч��
  /***
  **  p_optuserid    ������id
  **  p_parentid    ����id
  ***/
  procedure update_syscommtempdata(p_optuserid in varchar2,p_parentid in varchar2);

  --��2����������ʱ���е����ݸ��Ƶ���ʽ����
  /***
  **  p_optuserid    ������id
  **  p_parentid    ����id
  ***/
  procedure save_syscommtempdataintoreg(p_optuserid in varchar2,p_parentid in varchar2);

end ibmc_syn_community;
/
create or replace package body ibmc_syn_community is
    --��1����֤������Ϣ�������ݵ���Ч��
    /***
    **  p_optuserid    ������id
    **  p_parentid    ����id
    ***/
    procedure update_syscommtempdata(p_optuserid in varchar2,p_parentid in varchar2)is
         r_errstr varchar2(1000);
         r_errflag number(1):=1;     -- �����쳣��ʶ
         r_commtype1 number(1):=1;   -- ����ҵ����־
         r_commtype2 number(1):=2;   -- ����ҵ����־
         r_roomlev   number(1):=5;   -- ����㼶��ʶ
    begin
      -- ����commtype=1����ҵ����ҵ��ID
      update sys_community_temp t
         set t.ownerid = (select p.id from man_peo p where p.idcard = t.owneridcard and p.name = t.ownername)
      where t.optuserid = p_optuserid
        and t.parentid = p_parentid
        and t.commtype = r_commtype1;

      -- ��֤�ж�commtype=1����ҵ����ҵ��ID�Ƿ����ϵͳ��
      update sys_community_temp t
       set t.errorflag = r_errflag,
           t.errorstr =
                      (case
                         when (select count(1) from man_peo p where p.idcard = t.owneridcard)>0
                         then 'ҵ�������֤������������'
                         else 'ҵ����Ա���ݲ�����ϵͳ�У���¼��ҵ�����ݺ������ԣ�'
                      end)
      where t.optuserid = p_optuserid
        and t.parentid = p_parentid
        and t.commtype = r_commtype1
        and t.ownerid is null;

      -- ��֤���������Ƿ����� ����+���� ��ɵ�����
      update sys_community_temp t
         set t.errorflag = r_errflag,
             t.errorstr = '�������Ʋ����Ϲ淶'
      where t.optuserid = p_optuserid
        and exists (select 1 from sys_community_temp t2
        where optuserid = p_optuserid and t2.commlev = r_roomlev and t2.id = t.id
        start with parentid = p_parentid
        connect by prior id = parentid)
        and t.commlev = r_roomlev
        -- ���������ƴ��ڳ�'����'������ַ���ʱ�򷵻�0,���򷵻�1
        and nvl2(Translate(replace(t.commname, '����', ''), '\1234567890','\'),'0','1') = 0;

      commit;    -- �ύ����
    --�쳣����
    exception when others then
      begin
        rollback;
        dbms_output.put_line('��֤������Ϣ���ݳ����ˣ�');
        dbms_output.put_line(sqlerrm);
      end;
    end update_syscommtempdata;

    --��2����������ʱ���е����ݸ��Ƶ���ʽ����
    /***
    **  p_optuserid    ������id
    **  p_parentid    ����id
    ***/
    procedure save_syscommtempdataintoreg(p_optuserid in varchar2,p_parentid in varchar2) is
         r_commpath  varchar2(200); --����/����·��
         r_houseid  number(12);     --��¼����id
         r_tempid   number(12);     --��¼ÿһ�����ɵ�sequencesID
         r_temporder number(12);    --�����
         r_housercommpath varchar2(200); --����·��
         r_roomcommpath varchar2(200); --����·��
         r_houeslev number(1):=4;   --��������
         r_roomlev  number(1):=5;   --�������
         r_usesign  number(1):=1;   --���ñ�ʶ
         r_village  number(12):=abs(p_parentid);  --��ȡ��ʽ���з���������ID
      begin
        -- ��ȡ����parentid[����·��]
        select s.commpath into r_commpath from sys_community s where s.id = r_village;

        -- ��ȡ�˴ε��뵽��ʱ�����б�����
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
            -- ��Ϊ������¼ʱ
            if(d.commlev = r_houeslev) then
              r_houseid := r_tempid;
              -- ��ѯ�����������
              select count(1)+1 into r_temporder from sys_community where parentid = r_village and deletesign = 0;
              -- ����·��
              r_housercommpath :=  r_commpath||'/'||r_tempid;

              -- ��������ķ�����Ϣ����ʽ����
              insert into sys_community
                (id, parentid, commdetail, commname, ownerid, commtype, commlev, usesign, commorder, remark, commzip, commpath, createdate, updatedate)
              values
                (r_tempid, r_village, d.commdetail, d.commname, d.ownerid, d.commtype, d.commlev, r_usesign, r_temporder, nvl2(nvl2(d.telephone,'��ϵ�绰��'||d.telephone,''),'��ϵ�绰��'||d.telephone||'��','')||d.remark, d.commzip, r_housercommpath, sysdate, null);
            else
              -- ��ѯ����������
              select count(1)+1 into r_temporder from sys_community where parentid = r_houseid and deletesign = 0;
              -- ����·��
              r_roomcommpath := r_commpath||'/'||r_houseid||'/'||r_tempid;
              -- ��������ķ�����Ϣ����ʽ����
              insert into sys_community
                (id, parentid, commdetail, commname, ownerid, commtype, commlev, usesign, commorder, remark, commzip, commpath, createdate, updatedate)
              values
                (r_tempid, r_houseid, d.commdetail, d.commname, d.ownerid, d.commtype, d.commlev, r_usesign, r_temporder, nvl2(nvl2(d.telephone,'��ϵ�绰��'||d.telephone,''),'��ϵ�绰��'||d.telephone||'��','')||d.remark, d.commzip, r_roomcommpath, sysdate, null);
            end if;
       end loop;
       -- ɾ���˴ε��뵽��ʱ���е�����
       delete from sys_community_temp t
        where t.optuserid = p_optuserid
          and exists
              (select 1 from sys_community_temp t2
              where t2.optuserid = p_optuserid
              start with t2.parentid = p_parentid
              connect by prior t2.id = t2.parentid
              );
    --�쳣����
    exception when others then
      begin
        rollback;
        dbms_output.put_line('����ʱ���и��Ƶ���ʽ�������ݳ����ˣ�');
        dbms_output.put_line(sqlerrm);
      end;
    end save_syscommtempdataintoreg;

end ibmc_syn_community;
/
