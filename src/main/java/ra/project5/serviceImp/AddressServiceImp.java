package ra.project5.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.AddressPostRequest;
import ra.project5.model.dto.response.AddressResponse;
import ra.project5.model.dto.response.ProductResponse;
import ra.project5.model.entity.Address;
import ra.project5.model.entity.Product;
import ra.project5.model.entity.User;
import ra.project5.repository.AddressRepository;
import ra.project5.repository.UserRepository;
import ra.project5.service.AddressService;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public UserRepository userRepository;



    //USER


    @Override
    public AddressResponse saveAddressByUserId(AddressPostRequest addressPostRequest) throws CustomException {
       User user = userRepository.findById(addressPostRequest.getUserId())
                .orElseThrow(() -> new CustomException("UserId not found"));

        Address address = Address.builder()
                .user(user)
                .fullAddress(addressPostRequest.getFullAddress())
                .phone(addressPostRequest.getPhone())
                .receiveName(addressPostRequest.getReceiveName()).build();
        return modelMapper.map(addressRepository.save(address), AddressResponse.class);
    }

    @Override
    public List<AddressResponse> findAllSaveAddressByUserId(Long userId) {
        List<Address> listAddress = addressRepository.findAllByUserUserId(userId);
        List<AddressResponse> listAddressResponse = listAddress.stream()
                .map(address -> modelMapper.map(address, AddressResponse.class)).toList();
        return listAddressResponse;
    }

    @Override
    public AddressResponse findAddressById(Long userId, Long addressId) throws CustomException {
        Address addressNew = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException("AddressId not found"));
        if (addressNew.getUser().getUserId().equals(userId)){
            return modelMapper.map(addressNew, AddressResponse.class);
        }
        throw new CustomException("UserId not found");
    }
}
