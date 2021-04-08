package hector.developers.alabaster.api;

import java.util.List;
import java.util.Map;

import hector.developers.alabaster.model.Attendance;
import hector.developers.alabaster.model.Finance;
import hector.developers.alabaster.model.Members;
import hector.developers.alabaster.model.Request;
import hector.developers.alabaster.model.Users;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("member")
    Call<Members> createMember(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("phone") String phone,
            @Field("state") String state,
            @Field("gender") String gender,
            @Field("email") String email,
            @Field("address") String address,
            @Field("city") String city,
            @Field("occupation") String occupation
    );

    @FormUrlEncoded
    @POST("attendance")
    Call<Attendance> createAttendance(
            @Field("numberOfMales") String numberOfMales,
            @Field("numberOfFemales") String numberOfFemales,
            @Field("numberOfChildren") String numberOfChildren,
            @Field("date") String date,
            @Field("total") String total
    );

    @FormUrlEncoded
    @POST("finance")
    Call<Finance> addFinance(
            @Field("date") String date,
            @Field("day") String day,
            @Field("programme") String programme,
            @Field("tithe") String tithe,
            @Field("offering") String offering,
            @Field("firstFruit") String firstFruit,
            @Field("projectOrPledgeFund") String projectOrPledgeFund,
            @Field("others") String others,
            @Field("countingUsher") String countingUsher,
            @Field("receivedBy") String receivedBy,
            @Field("total") String total
    );

    @FormUrlEncoded
    @POST("request")
    Call<Request> createRequest(
            @Field("date") String date,
            @Field("nameOfRequester") String nameOfRequester,
            @Field("department") String department,
            @Field("amount") String amount,
            @Field("reason") String reason,
            @Field("approvalStatus") String approvalStatus
    );

    //the signIn call
    @FormUrlEncoded
    @POST("login")
    Call<Users> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("members")
    Call<List<Members>> getMembers();

    @GET("attendance")
    Call<List<Attendance>> getAttendance();

    @GET("finance")
    Call<List<Finance>> getAllFinance();

    @GET("request")
    Call<List<Request>> getAllRequest();

    @FormUrlEncoded
    @PUT("request/{id}")
    Call<Request>updateRequest(
            @Field("approvalStatus") String approvalStatus);
}