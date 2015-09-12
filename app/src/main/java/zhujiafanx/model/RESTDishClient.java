package zhujiafanx.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.File;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import zhujiafanx.app.App;
import zhujiafanx.helper.SessionManager;
import zhujiafanx.rest.IDishClient;
import zhujiafanx.rest.RestDishCategory;
import zhujiafanx.rest.RestDishItem;
import zhujiafanx.rest.RestLocation;

/**
 * Created by Administrator on 2015/7/3.
 */
public class RESTDishClient implements IDishClient {

    //private static String baseUri = "http://hweitao1990.xicp.net:3002";
    private static String baseUri="http://hweitao1990.nat123.net";

    private IDishService dishService;

    public RESTDishClient() {

        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(RestDishItem.class, new DishItemDeserializer());

        dishService = new RestAdapter.Builder().setEndpoint(baseUri)
                .setConverter(new GsonConverter(gson.create()))
                .setRequestInterceptor(new AuthenticationRequestInterceptor())
                .build().create(IDishService.class);
    }

    @Override
    public ArrayList<RestDishItem> GetDishItems(int page, int count) {
        ArrayList<RestDishItem> dishItems = dishService.GetDishItems(page, count);

        //ArrayList<RestDishItem> dishItems=new ArrayList<RestDishItem>();
        return dishItems;
    }

    @Override
    public ArrayList<RestDishCategory> GetDishCategories() {

        ArrayList<RestDishCategory> catagories = dishService.GetDishCatagories();

        return catagories;
    }

    @Override
    public void CreateDishItem(RestDishItem dishItem) {

        File file = new File(dishItem.ImageList.get(0));
        TypedFile typedFile=new TypedFile("image/jpeg",file);

        Gson gson=new Gson();
        TypedString typedJson=new TypedString(gson.toJson(dishItem).toString());
        dishService.CreateDishItem(typedFile, typedJson);
    }
}

interface IDishService
{
    @GET("/dish/catagory/list")
    ArrayList<RestDishCategory> GetDishCatagories();

    @GET("/dish/item/list")
    ArrayList<RestDishItem> GetDishItems(@Query("page") int page, @Query("count") int count);

    @POST("/dish/item")
    @Multipart
    RestDishItem CreateDishItem(@Part("images") TypedFile images, @Part("RESTDishItem") TypedString json);
}

class NetDateTimeAdapter implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String str = json.getAsString();

        Date result = null;

        str=str.replace("T"," ");
        if (!TextUtils.isEmpty(str)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                result = format.parse(str);
            }
            catch (ParseException e)
            {
            }
        }

        return result;
    }

//    @Override
//    public Date read(JsonReader reader) throws IOException {
//
//        reader.setLenient(true);//这个不设置会出错
//
//        if (reader.peek() == JsonToken.NULL) {
//            reader.nextNull();
//            return null;
//        }
//        Date result = null;
//        String str = reader.nextString();
//        str = str.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)","$1$2");
//        str = str.replace("T", " ");
//        str="2009-02-15 09:21:35.345";
//        if (!TextUtils.isEmpty(str)) {
//            try {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//                result = format.parse(str);
//            }
//            catch (ParseException e)
//            {
//            }
//        }
//
//        return result;
//    }
//    @Override
//    public void write(JsonWriter writer, Date value) throws IOException {
//        // Nah..
//    }
}

class DishItemDeserializer implements JsonDeserializer<RestDishItem>
{
    @Override
    public RestDishItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RestDishItem dishItem = new RestDishItem();

        try {

            dishItem.Id=UUID.fromString(json.getAsJsonObject().get("Id").getAsString());
            dishItem.Title=json.getAsJsonObject().get("Title").getAsString();
            dishItem.PublisherId=UUID.fromString(json.getAsJsonObject().get("PublisherId").getAsString());
            dishItem.From=json.getAsJsonObject().get("Publisher").getAsString();

            //从服务器返回的"Catagory"有拼写错误，不能修改这里的Catagory字符串
            JsonObject category = json.getAsJsonObject().get("Catagory").getAsJsonObject();
            dishItem.Category.Id=category.get("Id").getAsString();
            dishItem.Category.Name=category.get("Name").getAsString();
            dishItem.Category.Description=category.get("Description").getAsString();

            //dishItem.ImageList=new ArrayList<String>();
            JsonArray imageList=json.getAsJsonObject().get("Images").getAsJsonArray();



            for(JsonElement image :imageList)
            {
                dishItem.ImageList.add(image.getAsJsonObject().get("StorePath").getAsString());
            }

            Gson gson=new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
            //String date = json.getAsJsonObject().get("PublishDate").getAsString();
            JsonElement date = json.getAsJsonObject().get("PublishDate");

            JsonObject location=json.getAsJsonObject().get("Location").getAsJsonObject();
            dishItem.Location=new Gson().fromJson(location, RestLocation.class);

            dishItem.PublishedDate=gson.fromJson(date, Date.class);
        }
        catch (Exception ex)
        {
            Log.d("ddd","dd");
        }

        return dishItem;
    }
}

class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {

        SessionManager sessionManager = new SessionManager(App.getContext());

        request.addHeader("Authorization", "Bearer "+sessionManager.getToken());
    }
}