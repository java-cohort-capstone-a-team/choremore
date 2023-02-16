package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleReop extends JpaRepository<Battle, Long> {

    Battle findBattleByOp1_Id(long id);

}
