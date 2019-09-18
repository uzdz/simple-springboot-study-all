package com.uzdz.study;

import com.uzdz.study.mongodb.domain.MongoUser;
import com.uzdz.study.mongodb.repository.MongoUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mongoUser")
public class MongoUserController {

    private static Logger logger = LoggerFactory.getLogger(MongoUserController.class);

    @Autowired(required = false)
    MongoUserRepository mongoUserRepository;

    @GetMapping("/findAll")
    public List<MongoUser> findAll() {
        logger.info("您调用了findAll接口");
        return mongoUserRepository.findAll();
    }

    @GetMapping("/findByUserName")
    public List<MongoUser> findByUserName(@RequestParam String userName) {
        logger.info("您调用了findByUserName接口");
        return mongoUserRepository.findByName(userName);
    }

    @GetMapping("/getOne/{id}")
    public MongoUser getOne(@PathVariable("id") String id) {
        logger.info("您调用了getOne接口");
        Optional<MongoUser> optional = mongoUserRepository.findById(id);
        return optional.get();
    }

    @PutMapping("/saveOrUpdate")
    public String saveOrUpdate(@ModelAttribute MongoUser entity) {
        logger.info("您调用了saveOrUpdate接口");
        try {
            if (null == entity.getId()) {
                mongoUserRepository.insert(entity);
            } else {
                mongoUserRepository.save(entity);
            }

        } catch (Exception e) {
            logger.error("失败，原因：" + e.getMessage());
            return "error";
        }
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById( @PathVariable("id") String id) {
        logger.info("您调用了delete接口");
        try {
            mongoUserRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("失败，原因：" + e.getMessage());
            return "error";
        }
        return "success";
    }
}
