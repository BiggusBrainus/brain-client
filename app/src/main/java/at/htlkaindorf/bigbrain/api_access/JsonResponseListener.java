package at.htlkaindorf.bigbrain.api_access;

/**
 * Interface used for the requests
 * @version BigBrain v1
 * @since 19.05.2021
 * @author Nico Pessnegger
 */
public interface JsonResponseListener {
    public  void onSuccessJson(String response);
}
