package org.ephyra.acropolis.persistence

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""
}
