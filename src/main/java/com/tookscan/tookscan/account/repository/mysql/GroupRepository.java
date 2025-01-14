package com.tookscan.tookscan.account.repository.mysql;

import com.tookscan.tookscan.account.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
