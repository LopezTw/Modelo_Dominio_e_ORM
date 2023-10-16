package com.lopeztw.ltcommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lopeztw.ltcommerce.entities.User;
import com.lopeztw.ltcommerce.projections.UserDetailsProjection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email); // como ja temos um atributo email criado la tudo certin(email), esse nome de metado ja casa diretamente

	
	@Query(nativeQuery = true, value = """
			SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority
			FROM tb_user
			INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
			INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
			WHERE tb_user.email = :email
		""")
	List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
	
}
