package com.capital.dragon;

import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.REPO.EmploeeRepo;
import com.capital.dragon.service.ParseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class ParseTesting_Emploee {
	@Mock
	private EmploeeBillRepo emploeeBillRepo;
	@Mock
	private EmploeeRepo emploeeRepo;
    @Autowired
    private ParseService parseService;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void Test1() throws Exception {
		File uploadedFile = new File(MtsBillingApplication.UPLOAD_DIR + "/" + "empl.csv");
		// Mockito.when(emploeeRepo.findByPhone("")).thenReturn(new Emploee());
        parseService.parseEmploeesList(uploadedFile);

	}

}
