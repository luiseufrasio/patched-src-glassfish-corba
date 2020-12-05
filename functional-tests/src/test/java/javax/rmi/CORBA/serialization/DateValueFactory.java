/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 * Copyright (c) 1998-1999 IBM Corp. All rights reserved.
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

package javax.rmi.CORBA.serialization;

/**
* DateValueFactory.java
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from java/util/Date.idl
* 01 June 1999 20:22:16 o'clock GMT+00:00
*/

public interface DateValueFactory extends org.omg.CORBA.portable.ValueFactory
{
  Date create__ ();
  Date create__long__long__long (int arg0, int arg1, int arg2);
  Date create__long__long__long__long__long (int arg0, int arg1, int arg2, int arg3, int arg4);
  Date create__long__long__long__long__long__long (int arg0, int arg1, int arg2, int arg3, int arg4, int arg5);
  Date create__long_long (long arg0);
  Date create__CORBA_WStringValue (String arg0);
}
