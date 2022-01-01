// Hao Zhong
// AD1 - 202111
// MainActivity.java
package com.fullsail.adv1.zhonghao_04;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.fullsail.adv1.zhonghao_04.Fragments.ContactListFragment;
import com.fullsail.adv1.zhonghao_04.Fragments.DetailFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactListFragment.ContactClickListener {

    private final ArrayList<Contact> mContactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            loadContacts();
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.listFragmentContainerView, ContactListFragment.newInstance(mContactList), null).commit();
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    loadContacts();
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                            .add(R.id.listFragmentContainerView, ContactListFragment.newInstance(mContactList), null).commit();
                } else {
                    Log.i("Permission", "was denied!");
                }
            });

    private void loadContacts() {
        ContentResolver cr = getContentResolver();
        String[] idProjection = {ContactsContract.Contacts._ID};
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, idProjection, null, null, null);

        while (cursor.getCount() > 0 && cursor.moveToNext()) {
            int idClmIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String id = cursor.getString(idClmIdx);

            String[] nameProjection = {
                    ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                    ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                    ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
            };
            Cursor nameCur = cr.query(ContactsContract.Data.CONTENT_URI, nameProjection,
                    ContactsContract.Data.CONTACT_ID + " =?", new String[]{id}, null);
            String fName = null;
            String midName = null;
            String lName = null;
            while (nameCur.getCount() > 0 && nameCur.moveToNext()) {
                int fNameClmIdx = nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
                fName = nameCur.getString(fNameClmIdx);
                if (fName == null) {
                    fName = "Unknown";
                }

                int midNameClmIdx = nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME);
                midName = nameCur.getString(midNameClmIdx);

                int lNameClmIdx = nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
                lName = nameCur.getString(lNameClmIdx);
                if (lName == null) {
                    lName = "";
                }
            }
            nameCur.close();

            String priNum = "";
            String secNum = null;
            String[] phoneProjection = {
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.TYPE
            };
            Cursor phoneCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?", new String[]{id}, null);
            while (phoneCur.getCount() > 0 && phoneCur.moveToNext()) {
                int phoneClmIdx = phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number;
                number = phoneCur.getString(phoneClmIdx);
                int typeClmIdx = phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                int type = phoneCur.getInt(typeClmIdx);
                switch (type) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        priNum = number;
                        secNum = number;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        secNum = number;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        priNum = number;
                        break;
                }
            }
            phoneCur.close();

            String[] photoProjection = {
                    ContactsContract.CommonDataKinds.Photo.PHOTO
            };
            Cursor photoCur = cr.query(ContactsContract.Data.CONTENT_URI, photoProjection,
                    ContactsContract.Data.CONTACT_ID+ " =?", new String[]{id}, null);
            byte[] photoBlob = null;
            while (photoCur.getCount() > 0 && photoCur.moveToNext()) {
                int blobClmIdx = photoCur.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO);
                photoBlob = photoCur.getBlob(blobClmIdx);
            }
            photoCur.close();

            mContactList.add(new Contact(id, fName, lName, midName, priNum, secNum, photoBlob));
        }
        cursor.close();

    }

    @Override
    public void onContactClicked(String _id) {
        for (Contact person: mContactList) {
            if (person.getId().equals(_id)) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.detailFragmentContainerView, DetailFragment.newInstance(person), null).commit();
            }
        }
    }
}