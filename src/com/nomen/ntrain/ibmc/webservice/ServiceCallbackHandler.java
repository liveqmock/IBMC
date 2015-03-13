
/**
 * ServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.nomen.ntrain.ibmc.webservice;

    /**
     *  ServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for ownerRemove method
            * override this method for handling normal response from ownerRemove operation
            */
           public void receiveResultownerRemove(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ownerRemove operation
           */
            public void receiveErrorownerRemove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUnlockDevList method
            * override this method for handling normal response from getUnlockDevList operation
            */
           public void receiveResultgetUnlockDevList(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockDevListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUnlockDevList operation
           */
            public void receiveErrorgetUnlockDevList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for propertyPersonModify method
            * override this method for handling normal response from propertyPersonModify operation
            */
           public void receiveResultpropertyPersonModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.PropertyPersonModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from propertyPersonModify operation
           */
            public void receiveErrorpropertyPersonModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockPersonRelaInfoAdd method
            * override this method for handling normal response from unlockPersonRelaInfoAdd operation
            */
           public void receiveResultunlockPersonRelaInfoAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockPersonRelaInfoAdd operation
           */
            public void receiveErrorunlockPersonRelaInfoAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setCenterIP method
            * override this method for handling normal response from setCenterIP operation
            */
           public void receiveResultsetCenterIP(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.SetCenterIPResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setCenterIP operation
           */
            public void receiveErrorsetCenterIP(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockDevRelaInfoAdd method
            * override this method for handling normal response from unlockDevRelaInfoAdd operation
            */
           public void receiveResultunlockDevRelaInfoAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockDevRelaInfoAdd operation
           */
            public void receiveErrorunlockDevRelaInfoAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildAdd method
            * override this method for handling normal response from buildAdd operation
            */
           public void receiveResultbuildAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildAdd operation
           */
            public void receiveErrorbuildAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for alarmSearch method
            * override this method for handling normal response from alarmSearch operation
            */
           public void receiveResultalarmSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.AlarmSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from alarmSearch operation
           */
            public void receiveErroralarmSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildSegmentAdd method
            * override this method for handling normal response from buildSegmentAdd operation
            */
           public void receiveResultbuildSegmentAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSegmentAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildSegmentAdd operation
           */
            public void receiveErrorbuildSegmentAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for logout method
            * override this method for handling normal response from logout operation
            */
           public void receiveResultlogout(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.LogoutResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from logout operation
           */
            public void receiveErrorlogout(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for propertyPersonSearch method
            * override this method for handling normal response from propertyPersonSearch operation
            */
           public void receiveResultpropertyPersonSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.PropertyPersonSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from propertyPersonSearch operation
           */
            public void receiveErrorpropertyPersonSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for devRemove method
            * override this method for handling normal response from devRemove operation
            */
           public void receiveResultdevRemove(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.DevRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from devRemove operation
           */
            public void receiveErrordevRemove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ownerSearch method
            * override this method for handling normal response from ownerSearch operation
            */
           public void receiveResultownerSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ownerSearch operation
           */
            public void receiveErrorownerSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for alarmDeal method
            * override this method for handling normal response from alarmDeal operation
            */
           public void receiveResultalarmDeal(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.AlarmDealResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from alarmDeal operation
           */
            public void receiveErroralarmDeal(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockPersonRelaInfoModify method
            * override this method for handling normal response from unlockPersonRelaInfoModify operation
            */
           public void receiveResultunlockPersonRelaInfoModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockPersonRelaInfoModify operation
           */
            public void receiveErrorunlockPersonRelaInfoModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildRemove method
            * override this method for handling normal response from buildRemove operation
            */
           public void receiveResultbuildRemove(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildRemove operation
           */
            public void receiveErrorbuildRemove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildSegmentModify method
            * override this method for handling normal response from buildSegmentModify operation
            */
           public void receiveResultbuildSegmentModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSegmentModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildSegmentModify operation
           */
            public void receiveErrorbuildSegmentModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ownerAdd method
            * override this method for handling normal response from ownerAdd operation
            */
           public void receiveResultownerAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ownerAdd operation
           */
            public void receiveErrorownerAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildSegmentRemvoe method
            * override this method for handling normal response from buildSegmentRemvoe operation
            */
           public void receiveResultbuildSegmentRemvoe(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSegmentRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildSegmentRemvoe operation
           */
            public void receiveErrorbuildSegmentRemvoe(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildTypeModify method
            * override this method for handling normal response from buildTypeModify operation
            */
           public void receiveResultbuildTypeModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildTypeModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildTypeModify operation
           */
            public void receiveErrorbuildTypeModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetUnlock method
            * override this method for handling normal response from resetUnlock operation
            */
           public void receiveResultresetUnlock(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.ResetUnlockResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetUnlock operation
           */
            public void receiveErrorresetUnlock(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUnlockRecord method
            * override this method for handling normal response from getUnlockRecord operation
            */
           public void receiveResultgetUnlockRecord(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockRecordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUnlockRecord operation
           */
            public void receiveErrorgetUnlockRecord(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockSearch method
            * override this method for handling normal response from unlockSearch operation
            */
           public void receiveResultunlockSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockSearch operation
           */
            public void receiveErrorunlockSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveImageFilePath method
            * override this method for handling normal response from saveImageFilePath operation
            */
           public void receiveResultsaveImageFilePath(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.SaveImageFilePathResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveImageFilePath operation
           */
            public void receiveErrorsaveImageFilePath(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ownerModify method
            * override this method for handling normal response from ownerModify operation
            */
           public void receiveResultownerModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.OwnerModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ownerModify operation
           */
            public void receiveErrorownerModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildSearch method
            * override this method for handling normal response from buildSearch operation
            */
           public void receiveResultbuildSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildSearch operation
           */
            public void receiveErrorbuildSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for devStatusSearch method
            * override this method for handling normal response from devStatusSearch operation
            */
           public void receiveResultdevStatusSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.DevStatusSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from devStatusSearch operation
           */
            public void receiveErrordevStatusSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUnlockPersonRelaInfoList method
            * override this method for handling normal response from getUnlockPersonRelaInfoList operation
            */
           public void receiveResultgetUnlockPersonRelaInfoList(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.GetUnlockPersonRelaInfoListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUnlockPersonRelaInfoList operation
           */
            public void receiveErrorgetUnlockPersonRelaInfoList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reset method
            * override this method for handling normal response from reset operation
            */
           public void receiveResultreset(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.ResetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reset operation
           */
            public void receiveErrorreset(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildModify method
            * override this method for handling normal response from buildModify operation
            */
           public void receiveResultbuildModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildModify operation
           */
            public void receiveErrorbuildModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for phoneRecordSearch method
            * override this method for handling normal response from phoneRecordSearch operation
            */
           public void receiveResultphoneRecordSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.PhoneRecordSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from phoneRecordSearch operation
           */
            public void receiveErrorphoneRecordSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for alarmModeSet method
            * override this method for handling normal response from alarmModeSet operation
            */
           public void receiveResultalarmModeSet(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.AlarmModeSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from alarmModeSet operation
           */
            public void receiveErroralarmModeSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for devModify method
            * override this method for handling normal response from devModify operation
            */
           public void receiveResultdevModify(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.DevModifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from devModify operation
           */
            public void receiveErrordevModify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getImage method
            * override this method for handling normal response from getImage operation
            */
           public void receiveResultgetImage(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.GetImageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getImage operation
           */
            public void receiveErrorgetImage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for msgSend method
            * override this method for handling normal response from msgSend operation
            */
           public void receiveResultmsgSend(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.MsgSendResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from msgSend operation
           */
            public void receiveErrormsgSend(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for devAdd method
            * override this method for handling normal response from devAdd operation
            */
           public void receiveResultdevAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.DevAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from devAdd operation
           */
            public void receiveErrordevAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for restorePwd method
            * override this method for handling normal response from restorePwd operation
            */
           public void receiveResultrestorePwd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.RestorePwdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from restorePwd operation
           */
            public void receiveErrorrestorePwd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for propertyPersonAdd method
            * override this method for handling normal response from propertyPersonAdd operation
            */
           public void receiveResultpropertyPersonAdd(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.PropertyPersonAddResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from propertyPersonAdd operation
           */
            public void receiveErrorpropertyPersonAdd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockPersonRelaInfoRemove method
            * override this method for handling normal response from unlockPersonRelaInfoRemove operation
            */
           public void receiveResultunlockPersonRelaInfoRemove(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockPersonRelaInfoRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockPersonRelaInfoRemove operation
           */
            public void receiveErrorunlockPersonRelaInfoRemove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for buildSegmentSearch method
            * override this method for handling normal response from buildSegmentSearch operation
            */
           public void receiveResultbuildSegmentSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.BuildSegmentSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from buildSegmentSearch operation
           */
            public void receiveErrorbuildSegmentSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.LoginResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for propertyPersonRemove method
            * override this method for handling normal response from propertyPersonRemove operation
            */
           public void receiveResultpropertyPersonRemove(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.PropertyPersonRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from propertyPersonRemove operation
           */
            public void receiveErrorpropertyPersonRemove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unlockDevRelaInfoRemove method
            * override this method for handling normal response from unlockDevRelaInfoRemove operation
            */
           public void receiveResultunlockDevRelaInfoRemove(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.UnlockDevRelaInfoRemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unlockDevRelaInfoRemove operation
           */
            public void receiveErrorunlockDevRelaInfoRemove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for devSearch method
            * override this method for handling normal response from devSearch operation
            */
           public void receiveResultdevSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.DevSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from devSearch operation
           */
            public void receiveErrordevSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for msgSearch method
            * override this method for handling normal response from msgSearch operation
            */
           public void receiveResultmsgSearch(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.MsgSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from msgSearch operation
           */
            public void receiveErrormsgSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for restoreFac method
            * override this method for handling normal response from restoreFac operation
            */
           public void receiveResultrestoreFac(
                    com.nomen.ntrain.ibmc.webservice.ServiceStub.RestoreFacResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from restoreFac operation
           */
            public void receiveErrorrestoreFac(java.lang.Exception e) {
            }
                


    }
    