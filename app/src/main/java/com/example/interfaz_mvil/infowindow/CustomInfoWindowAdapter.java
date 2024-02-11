package com.example.interfaz_mvil.infowindow;

import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.interfaz_mvil.R;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;



    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_markers_opendata, null);
    }


    public void renderWindowText(Marker marker, View view) {



        // Reset the visibility of the image view
        ImageView image = view.findViewById(R.id.info_window_image);
        image.setVisibility(View.VISIBLE);

        String title = marker.getTitle();
        TextView titleUi = view.findViewById(R.id.info_window_title);



        String snippet = marker.getSnippet();
        TextView snippetUi = view.findViewById(R.id.info_window_snippet);

        Button favoritos = view.findViewById(R.id.favoritosBotonVentana);

        if (title != null && !title.equals("Mi posición")) {
            // Spannable string allows us to edit the formatting of the text.
            SpannableString titleText = new SpannableString(title);
            titleUi.setText(titleText);

        }else if(title.equals("Mi posición")){
            SpannableString titleText = new SpannableString(title);
            titleUi.setText(titleText);
            favoritos.setVisibility(GONE);
        }
        else {
            titleUi.setText("");
        }



        if (snippet != null && (snippet.startsWith("http") || snippet.startsWith("https"))) {
            // Load the image for camera markers


            Picasso.get().load(snippet).error(R.drawable.no_disponible).placeholder(R.drawable.no_disponible)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (marker != null && marker.isInfoWindowShown()) {
                                // Refresh the marker's info window if it's already shown
                                marker.hideInfoWindow();
                                marker.showInfoWindow();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            // Log or handle the error scenario
                            Log.e("Picasso Error", "Error loading image: " + e.getMessage());
                            image.setVisibility(View.GONE); // Hide the image view on error
                        }
                    });
            // Hide the snippet text view for camera markers
            snippetUi.setVisibility(View.GONE);
        } else {
            // Display the text for incidencias markers and hide the image view
            image.setVisibility(View.GONE);
            snippetUi.setVisibility(View.VISIBLE); // Ensure the text view is visible
            SpannableString snippetText = new SpannableString(snippet);
            snippetUi.setText(snippetText);
        }




    }



    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
