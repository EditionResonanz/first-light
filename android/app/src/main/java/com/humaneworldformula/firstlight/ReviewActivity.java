/*
 * Erstes Licht – Bewertungsfenster von Google Play direkt in der App (Version 1.0.2, 16.11.2026).
 *
 * Die Web-App ruft diese Seite über den Link
 *   intent://review#Intent;scheme=firstlight;package=com.humaneworldformula.firstlight;S.browser_fallback_url=…;end
 * auf, wenn jemand in der Store-App auf „Bei Google Play bewerten“ tippt. Sie ist unsichtbar (durchsichtiges Design)
 * und zeigt nur das Bewertungsfenster von Google Play über der App. Danach schließt sie sich, die App läuft weiter.
 *
 * Google zeigt das Fenster nicht immer (Kontingent je Nutzer). Kommt es nicht oder ist es nach weniger als einer
 * Sekunde schon wieder fertig, öffnet sich stattdessen die Seite der App im Play Store, damit der Knopf nie ins Leere läuft.
 * Es werden keine Daten erhoben oder gespeichert.
 */
package com.humaneworldformula.firstlight;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class ReviewActivity extends Activity {
    private static final long MIN_SHOWN_MS = 1000;
    private boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ReviewManager manager = ReviewManagerFactory.create(this);
        manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(Task<ReviewInfo> request) {
                if (isFinishing()) return;
                if (!request.isSuccessful() || request.getResult() == null) {
                    openStorePage();
                    finishOnce();
                    return;
                }
                final long start = SystemClock.elapsedRealtime();
                manager.launchReviewFlow(ReviewActivity.this, request.getResult())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> flow) {
                                if (SystemClock.elapsedRealtime() - start < MIN_SHOWN_MS) openStorePage();
                                finishOnce();
                            }
                        });
            }
        });
    }

    private void openStorePage() {
        String id = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + id)));
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + id)));
            } catch (ActivityNotFoundException ignored) {
                // Kein Store und kein Browser: dann bleibt einfach die App offen.
            }
        }
    }

    private void finishOnce() {
        if (done) return;
        done = true;
        finish();
        overridePendingTransition(0, 0);
    }
}
