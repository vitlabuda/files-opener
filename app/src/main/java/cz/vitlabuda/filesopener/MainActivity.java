package cz.vitlabuda.filesopener;

/*
SPDX-License-Identifier: BSD-3-Clause

Copyright (c) 2021 Vít Labuda. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
following conditions are met:
 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
    disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other materials provided with the distribution.
 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote
    products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.



The app icon was NOT copied from the Files app that this app is supposed to open. It was created manually, using the
"folder" clipart shipped with Android Studio, licensed under the Apache License Version 2.0.
*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String[][] FILES_APP_PACKAGE_COMBINATIONS = {
            {"com.google.android.documentsui", "com.google.android.documentsui.files.FilesActivity"},
            {"com.google.android.documentsui", "com.android.documentsui.files.FilesActivity"},
            {"com.android.documentsui", "com.google.android.documentsui.files.FilesActivity"},
            {"com.android.documentsui", "com.android.documentsui.files.FilesActivity"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        launchFilesApp();

        finish();
    }

    private void launchFilesApp() {
        boolean launchedSuccessfully = false;

        for(String[] combination : FILES_APP_PACKAGE_COMBINATIONS) {
            String package_name = combination[0], class_name = combination[1];

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(package_name, class_name));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);

                launchedSuccessfully = true;

            } catch (ActivityNotFoundException e) {}
        }

        if(!launchedSuccessfully)
            Toast.makeText(this, R.string.files_app_not_installed_errmsg, Toast.LENGTH_LONG).show();
    }
}
