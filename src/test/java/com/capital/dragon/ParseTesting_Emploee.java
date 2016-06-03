package com.capital.dragon;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.capital.dragon.REPO.EmploeeBillRepo;
import com.capital.dragon.REPO.EmploeeRepo;
import com.capital.dragon.model.Emploee;
import com.capital.dragon.service.ParseUploadedFile;

public class ParseTesting_Emploee {
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
		File uploadedFile = new File(MtsBillingApplication.UPLOAD_DIR + "/" + "empl.csv");
		// Mockito.when(emploeeRepo.findByPhone("")).thenReturn(new Emploee());
		ParseUploadedFile.parseEmploeesList(uploadedFile, emploeeRepo);

	}

}
