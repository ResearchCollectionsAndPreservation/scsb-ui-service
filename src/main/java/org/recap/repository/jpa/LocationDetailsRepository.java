package org.recap.repository.jpa;

import org.recap.model.jpa.LocationEntity;


public interface LocationDetailsRepository extends BaseRepository<LocationEntity> {

    /**
     *To get the location entity for the given location code.
     *
     * @param locationCode the institution code
     * @return the location entity
     */
    LocationEntity findByLocationCode(String locationCode);

    /**
     *To get the location entity for the given location name.
     *
     * @param locationName the location name
     * @return the location entity
     */
    LocationEntity findByLocationName(String locationName);

}
