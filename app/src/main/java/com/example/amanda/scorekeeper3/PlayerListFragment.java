package com.example.amanda.scorekeeper3;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Amanda on 1/5/2017.
 */

public class PlayerListFragment extends ListFragment implements View.OnClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // We want to allow modifications to the list so copy the dummy data array into an ArrayList
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0, z = PlayerArray.PLAYERS.length; i < z ; i++) {
            players.add(new Player(PlayerArray.PLAYERS[i]));
        }

        // Set the ListAdapter
        setListAdapter(new PopupAdapter(players));
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        Player item = (Player) listView.getItemAtPosition(position);

        // Show a toast if the user clicks on an item
        Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_SHORT).show();
        Log.e("Log tag", "did the click work?");
    }

    @Override
    public void onClick(final View view) {
        // We need to post a Runnable to show the popup to make sure that the PopupMenu is
        // correctly positioned. The reason being that the view may change position before the
        // PopupMenu is shown.
        view.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(view);
            }
        });
    }

    // BEGIN_INCLUDE(show_popup)
    private void showPopupMenu(View view) {
        final PopupAdapter adapter = (PopupAdapter) getListAdapter();

        // Retrieve the clicked item from view's tag
        final Player item = (Player) view.getTag();

        // Create a PopupMenu, giving it the clicked view for an anchor
        PopupMenu popup = new PopupMenu(getActivity(), view);

        // Inflate our menu resource into the PopupMenu's Menu
        popup.getMenuInflater().inflate(R.menu.player_menu, popup.getMenu());

        // Set a listener so we are notified if a menu item is clicked
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.e("Log", "Trying to remove...");
                switch (menuItem.getItemId()) {
                    case R.id.menu_remove:
                        // Remove the item from the adapter
                        adapter.remove(item);
                        Log.e("Log","Item removed");
                        return true;
                    case R.id.menu_change_score:
                        // Remove the item from the adapter
                        adapter.remove(item);
                        Log.e("Log","Item removed from change_score button");
                        return true;
                }
                return false;
            }
        });

        // Finally show the PopupMenu
        popup.show();
    }
    // END_INCLUDE(show_popup)

    /**
     * A simple array adapter that creates a list of cheeses.
     */
    class PopupAdapter extends ArrayAdapter<Player> {

        PopupAdapter(ArrayList<Player> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, container, false);
            }

            // Get the {@link Player} object located at this position in the list
            final Player currentPlayer = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID version_name
            TextView nameTextView = (TextView) listItemView.findViewById(android.R.id.text1);
            // Get the Miwok translation for the current Word object and
            // set this text on the Miwok TextView
            nameTextView.setText(currentPlayer.getName());

            // BEGIN_INCLUDE(button_popup)
            // Retrieve the popup button from the inflated view
            View popupButton = listItemView.findViewById(R.id.button_popup);

            // Set the item as the button's tag so it can be retrieved later
            popupButton.setTag(getItem(position));

            // Set the fragment instance as the OnClickListener
            popupButton.setOnClickListener(PlayerListFragment.this);
            // END_INCLUDE(button_popup)

            // Finally return the view to be displayed
            return listItemView;
        }
    }

}
