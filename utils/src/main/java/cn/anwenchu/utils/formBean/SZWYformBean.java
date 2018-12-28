package cn.anwenchu.utils.formBean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/5/30.
 */
@Data
@NoArgsConstructor
public class SZWYformBean {

    private String Message;
    private String Version;
    private String Format;
    private String Merchant;
    private String ECMerchantType;
    private String MerchantID;
    private String TrxRequest;
    private String TrxType;
    private String PaymentType;
    private String PaymentLinkType;
    private String ReceiveAccount;
    private String ReceiveAccName;
    private String NotifyType;
    private String ResultNotifyURL;


}
