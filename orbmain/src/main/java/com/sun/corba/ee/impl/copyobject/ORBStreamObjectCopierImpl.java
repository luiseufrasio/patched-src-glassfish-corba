/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License v2.0
 * w/Classpath exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause OR GPL-2.0 WITH
 * Classpath-exception-2.0
 */

package com.sun.corba.ee.impl.copyobject ;


import java.io.Serializable;
import java.rmi.Remote;

import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.CORBA.ORB ;

import com.sun.corba.ee.impl.util.Utility;
import org.glassfish.pfl.dynamic.copyobject.spi.ObjectCopier;

public class ORBStreamObjectCopierImpl implements ObjectCopier {

    public ORBStreamObjectCopierImpl( ORB orb ) 
    {
        this.orb = orb ;
    }

    public Object copy(Object obj, boolean debug) {
        return copy( obj ) ;
    }

    public Object copy(Object obj) {
        if (obj instanceof Remote) {
            // Yes, so make sure it is connected and converted
            // to a stub (if needed)...
            return Utility.autoConnect(obj,orb,true);
        }

        OutputStream out = (OutputStream)orb.create_output_stream();
        out.write_value((Serializable)obj);
        InputStream in = (InputStream)out.create_input_stream();
        return in.read_value();
    }

    private ORB orb;
}
