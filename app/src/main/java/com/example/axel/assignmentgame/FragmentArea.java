package com.example.axel.assignmentgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentArea extends Fragment {
	private TextView areaType;
	private ImageButton starred;
	private EditText areaDesc;
	private GameData gameData;
	private Area a;

	@Override
	public void onCreate(Bundle b) {

	    super.onCreate(b);
	    gameData = GameData.getInstance();
	    a = gameData.getLocation();
	}

	@Override
	public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
		View v = li.inflate(R.layout.fragment_area, parent, false);

		// Get references to all major UI elements.
		areaType = (TextView) v.findViewById(R.id.areaType);
		starred = (ImageButton) v.findViewById(R.id.starred);
		areaDesc = (EditText) v.findViewById(R.id.areaDesc);


		// Set area type to Town or Wilderness
		if (a.isTown())
        {
            areaType.setText("Town");
        }
        else
        {
            areaType.setText("Wilderness");
        }


        if (a.isStarred())
            starred.setBackgroundResource(R.mipmap.ic_star_on);
        else
            starred.setBackgroundResource(R.mipmap.ic_star_off);

		starred.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			    a = gameData.getLocation();

				if (!a.isStarred())
				{
				    a.setStarred(true);
                    starred.setBackgroundResource(R.mipmap.ic_star_on);
				}
				else if (a.isStarred())
                {
                    a.setStarred(false);
                    starred.setBackgroundResource(R.mipmap.ic_star_off);
                }
			}
		});

		areaDesc.setText(a.getDescription());

		return v;
	}

	public void updateFragment(GameData gD) {
        if (gD.getLocation().isTown())
        {
            areaType.setText("Town");
            areaDesc.setText(gD.getLocation().getDescription());
        }
        else
        {
            areaType.setText("Wilderness");
            areaDesc.setText(gD.getLocation().getDescription());
        }

        if (gD.getLocation().isStarred())
            starred.setBackgroundResource(R.mipmap.ic_star_on);
        else
            starred.setBackgroundResource(R.mipmap.ic_star_off);
    }

    public void displayArea(Area a) {
        if (a.isTown())
        {
            areaType.setText("Town");
            areaDesc.setText(a.getDescription());
        }
        else
        {
            areaType.setText("Wilderness");
            areaDesc.setText(a.getDescription());
        }

        if (a.isStarred())
            starred.setBackgroundResource(R.mipmap.ic_star_on);
        else
            starred.setBackgroundResource(R.mipmap.ic_star_off);
    }

    public String getAreaDesc() { return areaDesc.getText().toString(); }
}