package com.ericssonlabs;

import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yangguang on 16/4/6.
 */
public class NFCActivity extends Activity {
    private Button btn;
    private NfcAdapter nfcAdapter = null;
    private PendingIntent pi = null;
    private TextView promt = null;
    private TextView title = null;
    private WebView webView = null;
    private int NFC_RESULT_OK=7;
    Tag tagFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        promt = (TextView)findViewById(R.id.promt);
        // 获取默认的NFC控制器
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            promt.setText("设备不支持NFC！");
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            promt.setText("请在系统设置中先启用NFC功能！");
            finish();
            return;
        }
        pi = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.nfcAdapter == null)
            return;
        if (!this.nfcAdapter.isEnabled()) {
            promt.setText("请在系统设置中先启用NFC功能！");
        }
        this.nfcAdapter.enableForegroundDispatch(this, this.pi, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.nfcAdapter == null)
            return;
        this.nfcAdapter.disableForegroundDispatch(this);
        this.nfcAdapter.disableForegroundNdefPush(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        processIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nfc_demo, menu);
        return true;
    }

    private String bytesToHexString(byte[] src) {
        return bytesToHexString(src, true);
    }

    private String bytesToHexString(byte[] src, boolean isPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isPrefix) {
            stringBuilder.append("0x");
        }
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (byte srcByte : src) {
            buffer[0] = Character.toUpperCase(Character.forDigit((srcByte >>> 4) & 0x0F , 16));
            buffer[1] = Character.toUpperCase(Character.forDigit(srcByte & 0x0F , 16));
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    public void processIntent(Intent intent) {
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            promt.setTextColor(Color.BLUE);
            String metaInfo = "";

            String cardID = bytesToHexString(tagFromIntent.getId());
            cardID = cardID.substring(2,cardID.length());

            metaInfo += "卡片ID：" + cardID ;
            Toast.makeText(this, "找到卡片", Toast.LENGTH_SHORT).show();
            promt.setText(metaInfo);
    		if (cardID.equals("")) {
    			Toast.makeText(NFCActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
			}
    		else{
    		Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", cardID);
			resultIntent.putExtras(bundle);
			this.setResult(NFC_RESULT_OK, resultIntent);}

    		NFCActivity.this.finish();

        }
    }

}
