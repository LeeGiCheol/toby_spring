package me.gicheol.service;

import me.gicheol.dao.UserDao;
import me.gicheol.domain.Level;
import me.gicheol.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    private UserDao userDao;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void upgradeLevels() {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            upgradeLevelsInternal();

            this.transactionManager.commit(status);
        } catch (Exception e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    private void upgradeLevelsInternal() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        switch(currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level");
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    private void sendUpgradeEMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("leegicheolgc@gmail.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 업그레이드 되었습니다.");

        this.mailSender.send(mailMessage);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

}