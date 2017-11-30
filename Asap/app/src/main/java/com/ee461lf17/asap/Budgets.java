package com.ee461lf17.asap;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;


import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



import pub.devrel.easypermissions.EasyPermissions;

import static com.ee461lf17.asap.MainActivity.REQUEST_AUTHORIZATION;

/**
 * Utilities for managing budgets
 * Created by Paul Cozzi on 11/30/2017.
 */

public class Budgets {
    //Returns a map from budget names to sheets IDs for the given user.
    //Will map to the most recent instance of the budget with the given name
    public static Map<String, String> getUserBudgets(String email) {
        return null;
    }

    //Returns a map from account names to sheets IDs for the given user.
    public static Map<String, String> getUserAccounts(GoogleAccountCredential credential) {
        String email = credential.getSelectedAccountName();
        return null;
    }

    //Adds an expenditure to the budget sheet corresponding to the given ID
    public static void addExpenditure(final Activity callingActivity, GoogleAccountCredential credential, String budgetID, String category,
                                      double expense, String comment, String date) {
        String range = "A1";
        String valueInputOption = "USER_ENTERED";
        String insertDataOption = "INSERT_ROWS";

        ValueRange requestBody = new ValueRange();
        requestBody.set("range", "A1");
        Object[][] ray = {{"","","","","","","","","","","",category,expense,comment,date}};
        requestBody.set("values", new ArrayList<Object>(Arrays.asList(ray)));
        Sheets sheetsService;
        try {
            sheetsService = createSheetsService(credential);
            final Sheets.Spreadsheets.Values.Append request =
                    sheetsService.spreadsheets().values().append(budgetID, range, requestBody);
            request.setValueInputOption(valueInputOption);
            request.setInsertDataOption(insertDataOption);
            runRequestOnSeparateThread(callingActivity, request);
        } catch (Throwable t) {
            System.out.println("Caught an exception: " + t.toString());
        }
    }

    private static void runRequestOnSeparateThread(final Activity callingActivity, final Sheets.Spreadsheets.Values.Append request) {
        new Thread(new Runnable() {
            boolean retry = true;
            @Override
            public void run() {
                while(retry) {
                    try {
                        request.execute();
                        retry = false;
                    } catch (UserRecoverableAuthIOException e) {
                        System.out.println("Trying again for permissions");
                        callingActivity.startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                    } catch (Throwable t) {
                        System.out.println("Was unable to update sheet: " + t);
                        retry = false;
                    }
                }
            }
        }).start();
    }

    private static Sheets createSheetsService(GoogleAccountCredential credential) throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // TODO: Change placeholder below to generate authentication credentials. See
        // https://developers.google.com/sheets/quickstart/java#step_3_set_up_the_sample
        //
        // Authorize using one of the following scopes:
        //   "https://www.googleapis.com/auth/drive"
        //   "https://www.googleapis.com/auth/drive.file"
        //   "https://www.googleapis.com/auth/spreadsheets"

        //TODO: Figure out what should be put for ApplicationName
        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Asap")
                .build();
    }

    /**
     * Update a permission's role.
     *
     * @param service Drive API service instance.
     * @param fileId ID of the file to update permission for.
     * @param permissionId ID of the permission to update.
     * @param newRole The value "owner", "writer" or "reader".
     * @return The updated permission if successful, {@code null} otherwise.
     */
    private Permission updatePermission(Drive service, String fileId,
                                        String permissionId, String newRole) {
        try {
            // First retrieve the permission from the API.
            Permission permission = service.permissions().get(
                    fileId, permissionId).execute();
            permission.setRole(newRole);
            return service.permissions().update(
                    fileId, permissionId, permission).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;
    }
}