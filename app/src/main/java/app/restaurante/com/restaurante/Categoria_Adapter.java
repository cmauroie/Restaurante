package app.restaurante.com.restaurante;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Mauricio Idárraga Espitia on 17/06/2015.
 */
//Mirar como ampliar Volley Google
//http://b2cloud.com.au/tutorial/getting-a-server-response-status-200-from-android-volley-library/

public class Categoria_Adapter extends ArrayAdapter{

    private RequestQueue requestQueue;
    //JsonObjectRequest jsArrayRequest;
    JsonArrayRequest jsonArrayRequest;
    private static final String URL_BASE = "http://192.237.180.31/archies/public/";
    private static final String URL_JSON = "category";
    private static final String TAG = "Categoria_Adapter";
    List<Categoria> items;

    public Categoria_Adapter(Context context) {
        super(context, 0);

        requestQueue = Volley.newRequestQueue(context);

        //Peticion para el Json
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG,response.toString());
                        items = parseJson(response);
                        notifyDataSetChanged();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error|| Response Json: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size():0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View listItemView;


        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.layout,
                parent,
                false):convertView;


        Categoria item = items.get(position);

        TextView textoTitulo = (TextView)listItemView.findViewById(R.id.textoTitulo);
        final ImageView imagenCategoria = (ImageView)listItemView.findViewById(R.id.imagePost);

        textoTitulo.setText(item.getName());



        //Peticion para la imagen

        ImageRequest request = new ImageRequest(
                URL_BASE + item.getImg_path(),
                new Response.Listener<Bitmap>(){
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imagenCategoria.setImageBitmap(bitmap);
                    }
                },0,0,null,null,
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error en respuesta Bitmap: "+error.getMessage());
                    }
                }
        );

        requestQueue.add(request);
        return listItemView;
    }


    public List<Categoria> parseJson(JSONArray response){

        //Variables Locales
        List<Categoria> posts = new ArrayList();
        JSONArray jsonArray = null;

        try {
            // Obtener el array del objeto
          //  jsonArray = response.getJSONArray("items");
            jsonArray = new JSONArray(response.toString());

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);

                    Categoria post = new Categoria(
                            objeto.getString("name"),
                            objeto.getString("img_path"));
                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }

}
