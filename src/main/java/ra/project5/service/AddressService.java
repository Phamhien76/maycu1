package ra.project5.service;

import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.AddressPostRequest;
import ra.project5.model.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse saveAddressByUserId (AddressPostRequest addressPostRequest) throws CustomException;
    List<AddressResponse> findAllSaveAddressByUserId(Long userId);
    AddressResponse findAddressById (Long userId,Long addressId) throws CustomException;
}
