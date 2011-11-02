package org.gestaltfoundation.couchdb

import org.scalatest.{Spec,BeforeAndAfter}
import org.scalatest.matchers.ShouldMatchers

case class ObjectTest ( Type: String, title: String ) extends Entity

class DbTest extends Spec with BeforeAndAfter with ShouldMatchers {
    
    var conn: Connection = _
    var existing_db: Db = _

    before {
        conn = new Connection ( "localhost", 5984 )
        existing_db = conn.createDatabase ( "existingdb" )
    }

    after {
        conn.deleteDatabase ( "existingdb" )
    }

    describe ( "A valid database" ) {
        it ( "should return the appropriate metadata" ) {
            existing_db.exists should be ( true )
            existing_db.info.db_name should equal ( "existingdb" )
        }

        it ( "should store an object" ) {
            val obj = ObjectTest ( "test", "Test object" )
            val success = existing_db.save ( obj )
            success should be ( true )
            obj.id.isEmpty should be ( false )
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
