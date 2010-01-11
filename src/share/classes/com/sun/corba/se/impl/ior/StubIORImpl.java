/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2002-2010 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.sun.corba.se.impl.ior;

import java.io.ObjectOutputStream ;
import java.io.IOException ;
import java.io.Serializable ;

import java.util.Arrays ;

import org.omg.CORBA.ORB ;

import org.omg.CORBA.portable.Delegate ;
import org.omg.CORBA.portable.InputStream ;
import org.omg.CORBA.portable.OutputStream ;

// Be very careful: com.sun.corba imports must not depend on 
// PEORB internal classes in ways that prevent portability to
// other vendor's ORBs.
import com.sun.corba.se.spi.presentation.rmi.StubAdapter ;
import com.sun.corba.se.impl.orbutil.ORBUtility;
import com.sun.corba.se.spi.orbutil.ORBConstants;

/**
 * This class implements a very simply IOR representation
 * which must be completely ORBImpl free so that this class
 * can be used in the implementation of a portable StubDelegateImpl.
 */
public class StubIORImpl implements Serializable 
{
    private static final long serialVersionUID = -6261452601247416282L;
    // cached hash code
    transient private int hashCode = 0 ;
    
    // IOR components
    private byte[] typeData;
    private int[] profileTags;
    private byte[][] profileData;

    public StubIORImpl() 
    {
	typeData = null ;
	profileTags = null ;
	profileData = null ;
    }

    public String getRepositoryId()
    {
	if (typeData == null) {
	    return null ;
        }

	return new String( typeData ) ;
    }

    public StubIORImpl( org.omg.CORBA.Object obj ) {

	// All externally visible IOR representations must be handled
	// using the standard CDR encoding, irrespective of encoding setting.
	byte encodingVersion = ORBUtility.getEncodingVersion();
	if (encodingVersion != ORBConstants.CDR_ENC_VERSION) {
	    ORBUtility.pushEncVersionToThreadLocalState(ORBConstants.CDR_ENC_VERSION); // set
	}

	try {
	    // write the IOR to an OutputStream and get an InputStream 
	    OutputStream ostr = StubAdapter.getORB(obj).create_output_stream();
	    ostr.write_Object(obj);
	    InputStream istr = ostr.create_input_stream();

	    // read the IOR components back from the stream
	    int typeLength = istr.read_long();
	    typeData = new byte[typeLength];
	    istr.read_octet_array(typeData, 0, typeLength);
	    int numProfiles = istr.read_long();
	    profileTags = new int[numProfiles];
	    profileData = new byte[numProfiles][];
	    for (int i = 0; i < numProfiles; i++) {
		profileTags[i] = istr.read_long();
		profileData[i] = new byte[istr.read_long()];
		istr.read_octet_array(profileData[i], 0,
				      profileData[i].length);
	    }

	} finally {
	    if (encodingVersion != ORBConstants.CDR_ENC_VERSION) {
		ORBUtility.popEncVersionFromThreadLocalState(); // unset
	    }
	}

    }
   
    public Delegate getDelegate(ORB orb) {

	// All externally visible IOR representations must be handled
	// using the standard CDR encoding, irrespective of encoding setting.
	byte encodingVersion = ORBUtility.getEncodingVersion();
	if (encodingVersion != ORBConstants.CDR_ENC_VERSION) {
	    ORBUtility.pushEncVersionToThreadLocalState(ORBConstants.CDR_ENC_VERSION); // set
	}

	try {

	    // write IOR components to an org.omg.CORBA.portable.OutputStream 
	    OutputStream ostr = orb.create_output_stream();
	    ostr.write_long(typeData.length);
	    ostr.write_octet_array(typeData, 0, typeData.length);
	    ostr.write_long(profileTags.length);
	    for (int i = 0; i < profileTags.length; i++) {
		ostr.write_long(profileTags[i]);
		ostr.write_long(profileData[i].length);
		ostr.write_octet_array(profileData[i], 0,
				       profileData[i].length);
	    }
	    InputStream istr = ostr.create_input_stream() ;

	    // read the IOR back from the stream
	    org.omg.CORBA.Object obj = istr.read_Object();
	    return StubAdapter.getDelegate( obj ) ;

	} finally {
	    if (encodingVersion != ORBConstants.CDR_ENC_VERSION) {
		ORBUtility.popEncVersionFromThreadLocalState(); // unset
	    }
	}

    }

    // DO NOT MODIFY THIS METHOD - implements OMG standard behavior.
    public  void doRead( java.io.ObjectInputStream stream ) 
        throws IOException, ClassNotFoundException 
    {
	// read the IOR from the ObjectInputStream
	int typeLength = stream.readInt();
	typeData = new byte[typeLength];
	stream.readFully(typeData);
	int numProfiles = stream.readInt();
	profileTags = new int[numProfiles];
	profileData = new byte[numProfiles][];
	for (int i = 0; i < numProfiles; i++) {
	    profileTags[i] = stream.readInt();
	    profileData[i] = new byte[stream.readInt()];
	    stream.readFully(profileData[i]);
        }
    }

    // DO NOT MODIFY THIS METHOD - implements OMG standard behavior.
    public  void doWrite( ObjectOutputStream stream )
        throws IOException 
    {
	// write the IOR to the ObjectOutputStream
	stream.writeInt(typeData.length);
	stream.write(typeData);
	stream.writeInt(profileTags.length);
	for (int i = 0; i < profileTags.length; i++) {
	    stream.writeInt(profileTags[i]);
	    stream.writeInt(profileData[i].length);
	    stream.write(profileData[i]);
        }
    }

    /**
     * Returns a hash code value for the object which is the same for all stubs
     * that represent the same remote object.
     * @return the hash code value.
     */
    @Override
    public synchronized int hashCode() 
    {
	if (hashCode == 0) {

	    // compute the hash code
	    for (int i = 0; i < typeData.length; i++) {
		hashCode = hashCode * 37 + typeData[i];
	    }

	    for (int i = 0; i < profileTags.length; i++) {
	 	hashCode = hashCode * 37 + profileTags[i];
		for (int j = 0; j < profileData[i].length; j++) {
		    hashCode = hashCode * 37 + profileData[i][j];
		}
            }
	}

        return hashCode;    
    }

    private boolean equalArrays( byte[][] data1, byte[][] data2 ) 
    {
	if (data1.length != data2.length) {
	    return false ;
        }

	for (int ctr=0; ctr<data1.length; ctr++) {
	    if (!Arrays.equals( data1[ctr], data2[ctr] ))  {
		return false ;
            }
	}

	return true ;
    }

    @Override
    public boolean equals(java.lang.Object obj) 
    {
        if (this == obj) {
            return true;    
        }
        
        if (!(obj instanceof StubIORImpl)) {
            return false;            
        }
        
        StubIORImpl other = (StubIORImpl) obj;
        if (other.hashCode() != this.hashCode()) {
            return false;
        }

	return Arrays.equals( typeData, other.typeData ) &&
	    Arrays.equals( profileTags, other.profileTags ) &&
	    equalArrays( profileData, other.profileData ) ;
    }

    private void appendByteArray( StringBuffer result, byte[] data )
    {
	for ( int ctr=0; ctr<data.length; ctr++ ) {
	    result.append( Integer.toHexString( data[ctr] ) ) ;
	}
    }

    /**
     * Returns a string representation of this stub. Returns the same string
     * for all stubs that represent the same remote object.
     * "SimpleIORImpl[<typeName>,[<profileID>]data, ...]"
     * @return a string representation of this stub.
     */
    @Override
    public String toString() 
    {
	StringBuffer result = new StringBuffer() ;
	result.append( "SimpleIORImpl[" ) ;
	String repositoryId = new String( typeData ) ;
	result.append( repositoryId ) ;
	for (int ctr=0; ctr<profileTags.length; ctr++) {
	    result.append( ",(" ) ;
	    result.append( profileTags[ctr] ) ;
	    result.append( ")" ) ;
	    appendByteArray( result,  profileData[ctr] ) ;
	}

	result.append( "]" ) ;
	return result.toString() ;
    }
}
