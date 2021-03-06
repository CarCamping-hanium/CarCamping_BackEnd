package com.Hanium.CarCamping.repository;

import com.Hanium.CarCamping.domain.Region;
import com.Hanium.CarCamping.domain.entity.CampSite;
import com.Hanium.CarCamping.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface CampSiteRepository extends JpaRepository<CampSite,Long> {
    /*
    @Query("select c from CampSite c where c.region=:region order by c.score DESC ")
    List<CampSite> findByRegion(@RequestParam("region") String region);

     */
    List<CampSite> findByRegion(@RequestParam("region") Region region);

    Optional<CampSite> findByName(String name);


    List<CampSite> findByRegionOrderByScoreDesc(Region region);
    List<CampSite> findByRegionOrderByScoreAsc(Region region);
    @Query("select c from CampSite c where c.region = :location order by c.campsite_id ASC")
    List<CampSite> findByRegionOrderByCampsite_idAsc(@Param("location") Region location);

    @Query("select c from CampSite c where c.region = :location order by c.campsite_id DESC")
    List<CampSite> findByRegionOrderByCampsite_idDesc(@Param("location") Region location);

    List<CampSite> findByRegistrant(Member member);
    //정렬 필요
    @Query("select c from CampSite c join fetch c.registrant m where c.registrant.id=:member_id order by c.campsite_id DESC")
    List<CampSite> findByRegistrantOrderByCampsite_id(Long member_id);

    List<CampSite> findByNameContainingOrderByScoreDesc(String name);
    List<CampSite> findByNameContainingAndRegionOrderByScoreDesc(String name,Region region);

    List<CampSite> findAll();
    @Query("select c from CampSite c order by c.campsite_id DESC")
    List<CampSite> findAllByOrderByCampsite_idDesc();

    List<CampSite> findAllByOrderByScoreAsc();
    List<CampSite> findAllByOrderByScoreDesc();

}
