package com.wefox.paymentProcessor;

import com.wefox.domain.entity.Account;
import com.wefox.domain.entity.Payment;
import com.wefox.paymentProcessor.exception.PaymentException;
import com.wefox.paymentProcessor.service.PaymentService;
import com.wefox.repository.jpaRepository.AccountRepository;
import com.wefox.repository.jpaRepository.PaymentRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessorApplicationTests {

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private PaymentService paymentService;

	@Rule //initMocks
	public MockitoRule rule = MockitoJUnit.rule();

	private Account sampleAccount;

	private Payment samplePayment;


	@Before
	public void setup() {
		sampleAccount = new Account(1, "Sample", "sample@sample.com", LocalDate.now(), null, LocalDateTime.now());
		samplePayment = new Payment("1", sampleAccount, Payment.PaymentType.online, "1", BigDecimal.ZERO, LocalDateTime.now());
	}

	@Test
	public void createPaymentTest() throws PaymentException {
		when(accountRepository.findById(sampleAccount.getAccountId())).thenReturn(java.util.Optional.of(sampleAccount));
		paymentService.createPayment(samplePayment);

		verify(accountRepository, Mockito.times(1)).save(sampleAccount);
		verify(paymentRepository, Mockito.times(1)).save(samplePayment);

		assertNotNull(sampleAccount.getLastPaymentDate());
	}

	@Test(expected=PaymentException.class)
	public void createPaymentWithAccountNotFoundTest() throws PaymentException {
		when(accountRepository.findById(sampleAccount.getAccountId())).thenReturn(Optional.empty());
		paymentService.createPayment(samplePayment);
	}

	@Test
	public void validateOnlinePaymentTest() throws PaymentException {
		assertTrue(paymentService.validateOnlinePayment(samplePayment));
	}
}
