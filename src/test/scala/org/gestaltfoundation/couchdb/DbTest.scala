package org.gestaltfoundation.couchdb

import org.scalatest.{Spec,BeforeAndAfter}
import org.scalatest.matchers.ShouldMatchers

case class ObjectTest ( title: String, msg: String ) extends Entity ( "test" )

class DbTest extends Spec with BeforeAndAfter with ShouldMatchers {
    
    var conn: Connection = _
    var existing_db: Db = _
    var obj: ObjectTest = _

    before {
        conn = new Connection ( "localhost", 5984 )
        existing_db = conn.createDatabase ( "existingdb" )
        obj = ObjectTest ( "Test", "Test object" )
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
            val success = existing_db.save ( obj )
            success should be ( true )
            obj.id.isEmpty should be ( false )
            obj.revision.isEmpty should be ( false )
        }
        
        it ( "should destroy a saved object" ) {
            existing_db.save ( obj ) should be ( true )
            val success = existing_db.destroy ( obj )
            success should be ( true )
            obj.id.isEmpty should be ( true )
            obj.revision.isEmpty should be ( true )
        }
        
        it ( "should fail trying to destroy an unsaved object" ) {
            val unsaved_obj = ObjectTest ( "Test2", "test object" )
            obj.id.isEmpty should be ( true )
            obj.revision.isEmpty should be ( true )
            val success = existing_db.destroy ( unsaved_obj )
            success should be ( false )
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
