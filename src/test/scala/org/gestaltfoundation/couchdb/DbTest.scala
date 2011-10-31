package org.gestaltfoundation.couchdb

import org.scalatest.{Spec,BeforeAndAfter}
import org.scalatest.matchers.ShouldMatchers

class DbTest extends Spec with BeforeAndAfter with ShouldMatchers {
    
    var conn: Connection = _

    before {
        conn = new Connection ( "localhost", 5984 )
    }

    describe ( "A valid database" ) {
        it ( "should return the appropriate metadata" ) {
            conn.createDatabase ( "existingdb" )

            val db = conn("existingdb")
            db.exists should be ( true )
            db.info.db_name should equal ( "existingdb" )

            conn.deleteDatabase ( "existingdb" )
        }
    }

    describe ( "An invalid database" ) {
        it ( "should have exists return false" ) {
            val db = conn("nonexistentdb")
            db.exists should be ( false )
        }
    }

}

// vim: set ts=4 sw=4 et:
