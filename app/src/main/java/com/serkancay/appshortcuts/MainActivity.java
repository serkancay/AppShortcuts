package com.serkancay.appshortcuts;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDynamicShortcut();
        createPinShortcut();
    }

    private void createDynamicShortcut() {
        Intent intent = new Intent(this, AndroidActivity.class);
        intent.setAction(Intent.ACTION_VIEW);

        if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

            ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
                    .setShortLabel("Android")
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_android))
                    .setIntent(intent)
                    .build();

            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
        }
    }

    private void createPinShortcut() {
        Intent intent = new Intent(this, CreateActivity.class);
        intent.setAction(Intent.ACTION_VIEW);

        if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            ShortcutManager mShortcutManager =
                    this.getSystemService(ShortcutManager.class);

            if (mShortcutManager.isRequestPinShortcutSupported()) {
                ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id2")
                        .setShortLabel("Oluştur")
                        .setIcon(Icon.createWithResource(this, R.drawable.ic_create))
                        .setIntent(intent)
                        .build();

                Intent pinnedShortcutCallbackIntent =
                        mShortcutManager.createShortcutResultIntent(shortcut);

                PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
                        pinnedShortcutCallbackIntent, 0);

                mShortcutManager.requestPinShortcut(shortcut,
                        successCallback.getIntentSender());
            }

        }
    }
}
