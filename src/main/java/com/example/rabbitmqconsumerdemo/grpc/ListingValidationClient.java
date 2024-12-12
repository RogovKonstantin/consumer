package com.example.rabbitmqconsumerdemo.grpc;



import com.example.validation.ListingValidationProto;
import com.example.validation.ListingValidationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ListingValidationClient {


    public static ListingValidationProto.ValidationResponse validateListing(
            String title, String description) {


        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9600)
                .usePlaintext()
                .build();


        ListingValidationServiceGrpc.ListingValidationServiceBlockingStub stub =
                ListingValidationServiceGrpc.newBlockingStub(channel);


        ListingValidationProto.ListingRequest request = ListingValidationProto.ListingRequest.newBuilder()
                .setTitle(title)
                .setDescription(description)
                .build();


        ListingValidationProto.ValidationResponse response = stub.validateListing(request);
        channel.shutdown();
        return response;
    }
}
