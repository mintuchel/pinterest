package ensharp.pinterest.domain.emailverification.service;

import ensharp.pinterest.domain.emailverification.dto.request.EmailVerificationRequest;
import ensharp.pinterest.domain.emailverification.dto.request.VerificationCodeRequest;
import ensharp.pinterest.domain.emailverification.entity.EmailVerification;
import ensharp.pinterest.domain.emailverification.repository.EmailVerificationRepository;
import ensharp.pinterest.domain.emailverification.template.EmailTemplate;
import ensharp.pinterest.global.exception.errorcode.EmailErrorCode;
import ensharp.pinterest.global.exception.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;

    private final JavaMailSender emailSender;

    private static final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public String sendVerificationCode(VerificationCodeRequest verificationCodeRequest){
        String createdCode = createVerificationCode();
        sendVerificationCodeMail(verificationCodeRequest, createdCode);

        // 이미 존재한다면 최근 코드로 갱신
        if(emailVerificationRepository.existsById(verificationCodeRequest.email())){
            emailVerificationRepository.updateCodeById(verificationCodeRequest.email(), createdCode);
        }
        else {
            EmailVerification mail = EmailVerification.builder()
                    .email(verificationCodeRequest.email())
                    .verificationCode(createdCode)
                    .build();

            emailVerificationRepository.save(mail);
        }

        return "인증 번호가 발송되었습니다";
    }

    // SecureRandom 이용해서 6자리 인증코드 생성
    private String createVerificationCode() {
        return String.format("%06d", secureRandom.nextInt(1000000));
    }

    // 생성된 인증코드를 바탕으로 실제로 메일을 보내주는 함수
    public void sendVerificationCodeMail(VerificationCodeRequest verificationCodeRequest, String createdCode){
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(verificationCodeRequest.email());
            helper.setSubject(EmailTemplate.VERIFICATION_MAIL_TITLE);
            helper.setText(String.format(EmailTemplate.VERIFICATION_MAIL_CONTENT, createdCode), true);
            emailSender.send(message);
        } catch (RuntimeException | MessagingException e) {
            // 메시지 전송 시 에러 터지면 서버 에러임
            throw new EmailException(EmailErrorCode.MESSAGING_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public String verify(EmailVerificationRequest emailVerificationRequest) {

        // 이메일 인증 테이블에 이메일이 존재하지 않는다면
        // 애초에 인증코드가 전송된 적이 없다는 뜻
        EmailVerification emailVerification = emailVerificationRepository.findById(emailVerificationRequest.email())
                .orElseThrow(() -> new EmailException(EmailErrorCode.CODE_NOT_SENT));

        // 만약에 코드가 일치하지 않는다면
        if(!emailVerification.getVerificationCode().equals(emailVerificationRequest.verificationCode()))
            throw new EmailException(EmailErrorCode.INVALID_CODE);

        return "인증 번호가 확인되었습니다.";
    }
}
