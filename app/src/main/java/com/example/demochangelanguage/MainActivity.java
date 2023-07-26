package com.example.demochangelanguage;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		languageChange();
		setContentView(R.layout.activity_main);


		findViewById(R.id.btn).setOnClickListener(v -> LanguageClass.languageSettingDialog(this, language -> {
			LanguageClass.setLocale(this, language);
			this.recreate();
		}));
	}

	private void languageChange() {
		String language = LanguageClass.getLanguage(this);
		Toast.makeText(this, ""+language, Toast.LENGTH_SHORT).show();
		if (language != null) {
			LanguageClass.onCreate(this, language);
		}
		LanguageClass.setLocale(this, language);
	}

}