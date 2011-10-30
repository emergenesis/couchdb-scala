package org.gestaltfoundation.couchdb

import org.scalatest.{Spec,BeforeAndAfter}
import org.scalatest.matchers.ShouldMatchers

class ConnectionTest extends Spec with BeforeAndAfter with ShouldMatchers {
    
    var conn: Connection = _

    before {
        conn = new Connection ( "localhost", 5984 )
    }

    describe ( "A valid Connection" ) {
        it ( "should successfully connect to the specified CouchDB instance" ) {
            conn.couch_version should equal ( "1.2.0a-" )
        }
    }

    describe ( "An invalid Connection" ) {
        it ( "should throw an exception" ) {
            evaluating {
                new Connection ( "localhost", 1000 )
            } should produce [ConnectionException]
            
            evaluating {
                new Connection ( "badhost", 5984 )
            } should produce [ConnectionException]
        }
    }
}

// vim: set ts=4 sw=4 et:
