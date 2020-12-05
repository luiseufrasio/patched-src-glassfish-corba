/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
* org/omg/CORBA/OperationMode.java .
* IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
* IGNORE Sunday, January 21, 2018 1:54:23 PM EST
*/

public class OperationMode implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static org.omg.CORBA.OperationMode[] __array = new org.omg.CORBA.OperationMode [__size];

  public static final int _OP_NORMAL = 0;
  public static final org.omg.CORBA.OperationMode OP_NORMAL = new org.omg.CORBA.OperationMode(_OP_NORMAL);
  public static final int _OP_ONEWAY = 1;
  public static final org.omg.CORBA.OperationMode OP_ONEWAY = new org.omg.CORBA.OperationMode(_OP_ONEWAY);

  public int value ()
  {
    return __value;
  }

  public static org.omg.CORBA.OperationMode from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected OperationMode (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class OperationMode
