package com.example.demochangelanguage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

public class LanguageClass {

	public static final String LANG_ENGLISH = "en";
	public static final String LANG_HINDI = "hi";
	public static final String LANG_ARABIC = "ar";
	private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

	public static void languageSettingDialog(Activity context, final LanguageChangeCallback changeCallback) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater layoutInflater = context.getLayoutInflater();
		View view = layoutInflater.inflate(R.layout.dialog_change_language, null);

		final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);

		RadioButton radioButton_ENG = (RadioButton) view.findViewById(R.id.btn_eng);
		RadioButton radioButton_HNG = (RadioButton) view.findViewById(R.id.btn_hindi);
		Button btnSubmit =view.findViewById(R.id.btnSubmit);

		String selectedLanguage = getLanguage(context);

		Log.d("Tag", "selected Language" + selectedLanguage);
		if (selectedLanguage.equals("en"))
			radioButton_ENG.setChecked(true);

		if (selectedLanguage.equals("hi"))
			radioButton_HNG.setChecked(true);
		builder.setView(view);
		final AlertDialog alertDialog = builder.create();
		btnSubmit.setOnClickListener(v -> {
			alertDialog.dismiss();
			int selectedId = radioGroup.getCheckedRadioButtonId();

			switch (selectedId) {
				case R.id.btn_eng:
					changeCallback.onLanguageChange(LANG_ENGLISH);
					break;
				case R.id.btn_hindi:
					changeCallback.onLanguageChange(LANG_HINDI);
					break;
				case R.id.btn_arabic:
					changeCallback.onLanguageChange(LANG_ARABIC);
					break;
				default:
					changeCallback.onLanguageChange(LANG_ENGLISH);
					break;
			}
		});

		alertDialog.show();
	}

	public static String getLanguage(Context context) {
		return getPersistedData(context, Locale.getDefault().getLanguage());
	}

	private static String getPersistedData(Context context, String defaultLanguage) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
	}

	public static void setLocale(Context context) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			String selectedLanguage = preferences.getString(SELECTED_LANGUAGE, Locale.getDefault().getLanguage());
			persist(context, selectedLanguage);
			updateResources(context, selectedLanguage);
	}

	public static void onCreate(Context context, String defaultLanguage) {
		String lang = getPersistedData(context, defaultLanguage);
		setLocale(context, lang);
	}


	public static void setLocale(Context context, String language) {
		persist(context, language);
		updateResources(context, language);
	}

	private static void persist(Context context, String language) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(SELECTED_LANGUAGE, language);
		editor.apply();
	}
	private static void updateResources(Context context, String language) {
		Locale locale = new Locale(language);
		Locale.setDefault(locale);
		Resources resources = context.getResources();
		Configuration configuration = resources.getConfiguration();
		configuration.locale = locale;
		resources.updateConfiguration(configuration, resources.getDisplayMetrics());
	}
}
