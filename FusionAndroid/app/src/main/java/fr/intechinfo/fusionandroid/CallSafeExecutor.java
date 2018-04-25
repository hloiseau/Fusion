package fr.intechinfo.fusionandroid;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class CallSafeExecutor {
    private final Call call;
    public CallSafeExecutor(Call call){
        this.call = call;
    }
    public Response execute() throws Exception {
        try {
            return this.call.execute();
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}
