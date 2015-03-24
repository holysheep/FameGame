package gamecore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import gamecore.R;
import gamecore.network.VolleySingleton;

public class FragmentDummy extends Fragment {

    private TextView textView;

    public static FragmentDummy getInstance(int position) {
        FragmentDummy fragmentDummy = new FragmentDummy();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentDummy.setArguments(args);
        return fragmentDummy;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        textView = (TextView) layout.findViewById(R.id.position);
        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText("The Page Selected Is " + bundle.getInt("position"));
        }

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.GET, "http://www.androidhive.info/",
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });

        requestQueue.add(request);

        return layout;

    }
}
