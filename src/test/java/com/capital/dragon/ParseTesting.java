package com.capital.dragon;

import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.REPO.EmploeeRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

public class ParseTesting {
	@Mock
	private EmploeeBillRepo emploeeBillRepo;
	@Mock
	private EmploeeRepo emploeeRepo;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void Test1() throws Exception {
		File uploadedFile = new File(MtsBillingApplication.UPLOAD_DIR + "/inv.csv");
		// Mockito.when(emploeeRepo.findByPhone("")).thenReturn(new Emploee());
		//DecimalFormat df = new DecimalFormat("#.###");		;
		//System.out.println(Math.round(149.99*100.00)/100.00);	

        //ParseService.parseEmploeesBills(uploadedFile);


    }

}
