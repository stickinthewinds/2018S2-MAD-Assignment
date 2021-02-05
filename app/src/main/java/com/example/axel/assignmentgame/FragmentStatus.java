package com.example.axel.assignmentgame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class FragmentStatus extends Fragment {
	private TextView moneyView;
	private TextView healthView;
	private TextView massView;
	private TextView winView;
	private GameData gameData;
	private Player p;
	private Button restartBtn;
	private Item jade;
	private Item map;
	private Item scraper;

	@Override
	public void onCreate(Bundle b) {
	    super.onCreate(b);
        gameData = GameData.getInstance();
        jade = new Equipment("Jade Monkey", "A beautiful monkey. One of the requirements to win.", 5, 5.0, "none");
        map = new Equipment("Roadmap", "A map of the area. One of the requirements to win.", 5, 5.0, "none");
        scraper = new Equipment("Ice Scraper", "Scrapes ice. One of the requirements to win.", 5, 5.0, "none");
	}

	@Override
	public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
		View v = li.inflate(R.layout.fragment_status, parent, false);

		// Get references to all major UI elements.
		moneyView = (TextView) v.findViewById(R.id.money);
		healthView = (TextView) v.findViewById(R.id.health);
		massView = (TextView) v.findViewById(R.id.mass);
		winView = (TextView) v.findViewById(R.id.winStatus);
		restartBtn = (Button) v.findViewById(R.id.restartBtn);

		p = gameData.getPlayer();

        double roundedHealth = (double)Math.round(p.getHealth() * 100)/100, roundedMass = (double)Math.round(p.getEquipmentMass() * 100)/100;

        moneyView.setText("Cash: " + p.getCash());
        healthView.setText(" Health: " + roundedHealth + " ");
        massView.setText(" Weight: " + roundedMass + " ");

		if (p.getHealth() == 0)
		{
			GameToasts.makeToast(getContext(), "You lose.");
			winView.setText("Lose");
		}
		else if (GameData.winCondition())
		//else if (currItems.contains(jade) && currItems.contains(map) && currItems.contains(scraper))
		{
			GameToasts.makeToast(getContext(), "You win.");
			winView.setText("Win");
		}
		else
		{
			winView.setText("Play");
		}

		restartBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				GameData.destroySingleton();
				startActivity(MainActivity.getIntent(getActivity()));
			}
		});

		return v;
	}

	public void updateFragment(GameData gD)
    {
        Player player = gD.getPlayer();
		List<Equipment> currItems = player.getEquipment();

		double roundedHealth = (double)Math.round(p.getHealth() * 100)/100, roundedMass = (double)Math.round(p.getEquipmentMass() * 100)/100;

		moneyView.setText("Cash: " + player.getCash());
        healthView.setText(" Health: " + roundedHealth + " ");
        massView.setText(" Weight: " + roundedMass + " ");

        if (player.getHealth() == 0)
		{
			GameToasts.makeToast(getContext(), "You lose.");
			winView.setText("Lose");
            GameData.destroySingleton();
            startActivity(MainActivity.getIntent(getActivity()));
		}
		else if (GameData.winCondition())
		//else if (currItems.contains(jade) && currItems.contains(map) && currItems.contains(scraper))
		{
            GameToasts.makeToast(getContext(), "You win.");
			winView.setText("Win");
		}
		else
		{
			winView.setText("Play");
		}
    }
}