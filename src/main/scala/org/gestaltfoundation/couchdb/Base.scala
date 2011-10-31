package org.gestaltfoundation.couchdb

import dispatch._

case class Error ( val error: String, val reason: String )
case class NewSuccess ( ok: Boolean, id: String, rev: String )

trait Base {

    def get ( urlstring: String ) = {
        try {
            val http = new Http
            val resp = http ( url ( urlstring ) as_str )
            http.shutdown
            new Response ( resp )
        } catch {
            case e: StatusCode => new Response ( e.contents )
            case e => throw ConnectionException (
                "Could not connect to CouchDB server at %s; exception was %s".
                format ( urlstring, e.getClass.getName ) 
            )
        }
    }

    def put ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).PUT.as_str )
        http.shutdown
        new Response ( resp )
    }

    def delete ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).DELETE.as_str )
        http.shutdown
        new Response ( resp )
    }

    def post ( urlstring: String, data: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).POST.<<( data, "application/json" ).as_str )
        http.shutdown
        new Response( resp )
    }
}

// vim: set ts=4 sw=4 et:
