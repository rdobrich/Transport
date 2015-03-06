package hr.aktiva_info.transport.data;

import android.content.Intent;

/**
 * Created by radovan on 6.3.2015..
 */
public class Barcode {
        String data;
        String label_type;
        String source;


    // Let's define some intent strings
    // This intent string contains the source of the data as a string
    public static final String SOURCE_TAG = "com.motorolasolutions.emdk.datawedge.source";
    // This intent string contains the barcode symbology as a string
    public static final String LABEL_TYPE_TAG = "com.motorolasolutions.emdk.datawedge.label_type";
    // This intent string contains the barcode data as a byte array list
    public static final String DECODE_DATA_TAG = "com.motorolasolutions.emdk.datawedge.decode_data";

    // This intent string contains the captured data as a string
    // (in the case of MSR this data string contains a concatenation of the track data)
    public static final String DATA_STRING_TAG = "com.motorolasolutions.emdk.datawedge.data_string";

    // Let's define the MSR intent strings (in case we want to use these in the future)
    public static final String MSR_DATA_TAG = "com.motorolasolutions.emdk.datawedge.msr_data";
    public static final String MSR_TRACK1_TAG = "com.motorolasolutions.emdk.datawedge.msr_track1";
    public static final String MSR_TRACK2_TAG = "com.motorolasolutions.emdk.datawedge.msr_track2";
    public static final String MSR_TRACK3_TAG = "com.motorolasolutions.emdk.datawedge.msr_track3";
    public static final String MSR_TRACK1_STATUS_TAG = "com.motorolasolutions.emdk.datawedge.msr_track1_status";
    public static final String MSR_TRACK2_STATUS_TAG = "com.motorolasolutions.emdk.datawedge.msr_track2_status";
    public static final String MSR_TRACK3_STATUS_TAG = "com.motorolasolutions.emdk.datawedge.msr_track3_status";

    // Let's define the API intent strings for the soft scan trigger
    public static final String ACTION_SOFTSCANTRIGGER = "com.motorolasolutions.emdk.datawedge.api.ACTION_SOFTSCANTRIGGER";
    public static final String EXTRA_PARAM = "com.motorolasolutions.emdk.datawedge.api.EXTRA_PARAMETER";
    public static final String DWAPI_START_SCANNING = "START_SCANNING";
    public static final String DWAPI_STOP_SCANNING = "STOP_SCANNING";
    public static final String DWAPI_TOGGLE_SCANNING = "TOGGLE_SCANNING";

    private static String ourIntentAction = "";

    public Barcode(String ourIntentAction,String data, String label_type, String source) {
        this.data = data;
        this.label_type = label_type;
        this.source = source;
        this.ourIntentAction=ourIntentAction;
    }

    public Barcode(String ourIntentAction) {
        this.ourIntentAction=ourIntentAction;
    }

    public Integer getLength (){
        return data.length();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLabel_type() {
        return label_type;
    }

    public void setLabel_type(String label_type) {
        this.label_type = label_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }



    // This function is responsible for getting the data from the intent
    // formatting it and adding it to the end of the edit box
    public Barcode handleDecodeData(Intent i) {
        // check the intent action is for us
        String sLabelType="";
        if ( i.getAction().contentEquals(ourIntentAction) ) {
            // define a string that will hold our output

            String out = "";
            // get the source of the data
            String source = i.getStringExtra(SOURCE_TAG);
            // save it to use later
            if (source == null) source = "scanner";
            // get the data from the intent
            String data = i.getStringExtra(DATA_STRING_TAG);
            // let's define a variable for the data length
            Integer data_len = 0;
            // and set it to the length of the data
            if (data != null) data_len = data.length();

            // check if the data has come from the barcode scanner
            if (source.equalsIgnoreCase("scanner")) {
                // check if there is anything in the data
                if (data != null && data.length() > 0) {
                    // we have some data, so let's get it's symbology
                    sLabelType = i.getStringExtra(LABEL_TYPE_TAG);
                    // check if the string is empty
                    if (sLabelType != null && sLabelType.length() > 0) {
                        // format of the label type string is LABEL-TYPE-SYMBOLOGY
                        // so let's skip the LABEL-TYPE- portion to get just the symbology
                        sLabelType = sLabelType.substring(11);
                    }
                    else {
                        // the string was empty so let's set it to "Unknown"
                        sLabelType = "Unknown";
                    }
                    // let's construct the beginning of our output string
                    out = "Source: Scanner, " + "Symbology: " + sLabelType + ", Length: " + data_len.toString() + ", Data: ...\r\n";
                }
            }

            // check if the data has come from the MSR
            if (source.equalsIgnoreCase("msr")) {
                // construct the beginning of our output string
                out = "Source: MSR, Length: " + data_len.toString() + ", Data: ...\r\n";
            }
            this.data=data;
            this.label_type=sLabelType;
            this.source=source;


        }
        return this;
    }

}
