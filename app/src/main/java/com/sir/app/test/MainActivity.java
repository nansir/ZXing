package com.sir.app.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sir.library.base.BaseActivity;
import com.sir.library.zxing.ScanCodeActivity;
import com.sir.library.zxing.utils.CodeUtils;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    final int SCAN_CODE = 1001;
    @BindView(R.id.result)
    TextView result;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness(Context mContext) {
        checkPermissions();
    }

    private void checkPermissions() {
        String[] perms = new String[]{Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(MainActivity.this, perms)) {
            EasyPermissions.requestPermissions(MainActivity.this, "扫码相机权限", 100, perms);
        }
    }

    public void code(View view) {
        getOperation().forward(ScanCodeActivity.class, SCAN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCAN_CODE:
                if (resultCode == RESULT_OK) {
                    getSubscriber(data);
                }
                break;
        }
    }

    public void getSubscriber(Intent data) {
        if (data.getIntExtra(CodeUtils.RESULT_TYPE, 0) == 1) {
            String resultStr = data.getStringExtra(CodeUtils.RESULT_STRING);
            result.setText(resultStr);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "在权限授予", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == 100 && EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.permissions_camera))
                    .setRationale(getString(R.string.permissions))
                    .setPositiveButton(getString(R.string.setting))
                    .build().show();
        }
    }

}
