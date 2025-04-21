package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b join fetch b.item i join fetch b.booker u join fetch i.owner o where o.id = :ownerId order by b.start desc")
    List<Booking> findAllByOwnerId(@Param("ownerId") Long ownerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u join fetch i.owner o where o.id = :ownerId and current_timestamp between b.start and b.end order by b.start desc")
    List<Booking> findAllByOwnerIdAndCurrentTimestamp(@Param("ownerId") Long ownerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u join fetch i.owner o where o.id = :ownerId and b.end < current_timestamp order by b.start desc")
    List<Booking> findAllByOwnerIdAndPastTimestamp(@Param("ownerId") Long ownerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u join fetch i.owner o where o.id = :ownerId and b.start > current_timestamp order by b.start desc")
    List<Booking> findAllByOwnerIdAndFutureTimestamp(@Param("ownerId") Long ownerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u join fetch i.owner o where o.id = :ownerId and b.status = :status order by b.start desc")
    List<Booking> findAllByOwnerIdAndStatus(@Param("ownerId") Long ownerId, @Param("status") BookingStatus status);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u where u.id = :bookerId order by b.start desc")
    List<Booking> findAllByBookerId(@Param("bookerId") Long bookerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u where u.id = :bookerId and current_timestamp between b.start and b.end order by b.start desc")
    List<Booking> findAllByBookerIdAndCurrentTimestamp(@Param("bookerId") Long bookerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u where u.id = :bookerId and b.end < current_timestamp order by b.start desc")
    List<Booking> findAllByBookerIdAndPastTimestamp(@Param("bookerId") Long bookerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u where u.id = :bookerId and b.start > current_timestamp order by b.start desc")
    List<Booking> findAllByBookerIdAndFutureTimestamp(@Param("bookerId") Long bookerId);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u where u.id = :bookerId and b.status = :status order by b.start desc")
    List<Booking> findAllByBookerIdAndStatus(@Param("bookerId") Long bookerId, @Param("status") BookingStatus status);

    @Query("select b from Booking b join fetch b.item i join fetch b.booker u where i.id = :itemId and u.id = :bookerId")
    List<Booking> findAllByItemIdAndBookerId(@Param("itemId") Long itemId, @Param("bookerId") Long bookerId);
}
