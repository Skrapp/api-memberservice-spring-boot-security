package com.nilsson.wigellApi.config;

import com.nilsson.wigellApi.entity.Address;
import com.nilsson.wigellApi.entity.Member;
import com.nilsson.wigellApi.repository.AddressRepo;
import com.nilsson.wigellApi.repository.AppUserRepo;
import com.nilsson.wigellApi.repository.MemberRepo;
import com.nilsson.wigellApi.security.AppUser;
import com.nilsson.wigellApi.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initData(AppUserRepo appUserRepo, MemberRepo memberRepo, AddressRepo addressRepo, PasswordEncoder passwordEncoder){
        return args -> {
            if(appUserRepo.count() == 0){
                Address address1 = new Address("Mystikgatan 17", "123 45", "Gnarp");
                Address address2 = new Address("Långvägen 469", "899 90", "Njurunda");
                Address address3 = new Address("Hasselavägen 7", "887 76", "Hassela");
                Address address4 = new Address("Gammelgatan 45", "657 90", "Bergsjö");
                addressRepo.saveAll(List.of(address1, address2, address3, address4));

                Member member1 = new Member("Stefan", "Fredriksson", address1,"staffan@mail.com","076-345 40 70", LocalDate.of(1965, 8, 16));
                Member member2 = new Member("Majsen", "Fredriksson", address1,"majali@mail.com","070-444 55 66", LocalDate.of(1963, 10, 16));
                Member member3 = new Member("Lotta", "Jonsson", address2,"lotta.j@mail.com","077-8880955", LocalDate.of(1963, 4, 16));
                Member member4 = new Member("Robert", "Karlsson", address3,"karlssonr@mail.com","073-1212342", LocalDate.of(1999, 8, 28));
                Member member5 = new Member("Hannes", "Jonsson", address4,"sennah@mail.com","070-005 4565", LocalDate.of(2001, 2, 12));
                memberRepo.saveAll(List.of(member1, member2, member3, member4, member5));

                AppUser admin1 = new AppUser("staffan", passwordEncoder.encode("1234"), Set.of(Role.ADMIN), member1);
                AppUser admin2 = new AppUser("majali", passwordEncoder.encode("1234"), Set.of(Role.ADMIN), member2);
                AppUser user1 = new AppUser("lottajonsson", passwordEncoder.encode("asdf"), Set.of(Role.USER), member3);
                AppUser user2 = new AppUser("robertkarlsson", passwordEncoder.encode("asdf"), Set.of(Role.USER), member4);
                AppUser user3 = new AppUser("hannesjonsson", passwordEncoder.encode("asdf"), Set.of(Role.USER), member5);
                appUserRepo.saveAll(List.of(admin1, admin2, user1, user2, user3));
            }
        };
    }
}
