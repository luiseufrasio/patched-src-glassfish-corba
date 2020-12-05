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

package org.omg.CORBA;

/**
 * The <code>DynUnion</code> interface represents a <code>DynAny</code> object
 * that is associated with an IDL union.
 * Union values can be traversed using the operations defined in <code>DynAny</code>.
 * The first component in the union corresponds to the discriminator;
 * the second corresponds to the actual value of the union.
 * Calling the method <code>next()</code> twice allows you to access both components.
 * @deprecated Use the new <a href="../DynamicAny/DynUnion.html">DynUnion</a> instead
 */

// @Deprecated
public interface DynUnion extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * Determines whether the discriminator associated with this union has been assigned
     * a valid default value.
     * @return <code>true</code> if the discriminator has a default value;
     * <code>false</code> otherwise
     */
    public boolean set_as_default();

    /**
    * Determines whether the discriminator associated with this union gets assigned
    * a valid default value.
    * @param arg <code>true</code> if the discriminator gets assigned a default value
    */
    public void set_as_default(boolean arg);

    /**
    * Returns a DynAny object reference that must be narrowed to the type
    * of the discriminator in order to insert/get the discriminator value.
    * @return a <code>DynAny</code> object reference representing the discriminator value
    */
    // @SuppressWarnings({"deprecation"})
    public org.omg.CORBA.DynAny discriminator();

    /**
    * Returns the TCKind object associated with the discriminator of this union.
    * @return the <code>TCKind</code> object associated with the discriminator of this union
    */
    public org.omg.CORBA.TCKind discriminator_kind();

    /**
    * Returns a DynAny object reference that is used in order to insert/get
    * a member of this union.
    * @return the <code>DynAny</code> object representing a member of this union
    */
    // @SuppressWarnings({"deprecation"})
    public org.omg.CORBA.DynAny member();

    /**
    * Allows for the inspection of the name of this union member
    * without checking the value of the discriminator.
    * @return the name of this union member
    */
    public String member_name();

    /**
    * Allows for the assignment of the name of this union member.
    * @param arg the new name of this union member
    */
    public void member_name(String arg);

    /**
    * Returns the TCKind associated with the member of this union.
    * @return the <code>TCKind</code> object associated with the member of this union
    */
    public org.omg.CORBA.TCKind member_kind();
}
