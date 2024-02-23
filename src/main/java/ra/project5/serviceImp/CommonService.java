package ra.project5.serviceImp;

import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
public class CommonService {
    public Date convertToSqlDate(java.util.Date utilDate) {
        return new Date(utilDate.getTime());
    }
}
