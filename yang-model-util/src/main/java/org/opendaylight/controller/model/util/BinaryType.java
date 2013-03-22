/*
  * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
  *
  * This program and the accompanying materials are made available under the
  * terms of the Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  */
package org.opendaylight.controller.model.util;

import java.util.Collections;
import java.util.List;

import org.opendaylight.controller.model.api.type.BinaryTypeDefinition;
import org.opendaylight.controller.model.api.type.LengthConstraint;
import org.opendaylight.controller.yang.common.QName;
import org.opendaylight.controller.yang.model.api.ExtensionDefinition;
import org.opendaylight.controller.yang.model.api.SchemaPath;
import org.opendaylight.controller.yang.model.api.Status;

public class BinaryType implements BinaryTypeDefinition {

    private final QName name = BaseTypes.constructQName("binary");
    private final SchemaPath path = BaseTypes.schemaPath(name);
    private final String description = "";
    private final String reference = "";

    private List<Byte> bytes;
    private final LengthConstraint lengthConstraint;
    private String units = "";

    public BinaryType() {
        super();

        lengthConstraint = BaseConstraints.lengthConstraint(0L, Long.MAX_VALUE,
                null, null);
        bytes = Collections.emptyList();
        bytes = Collections.unmodifiableList(bytes);
    }

    public BinaryType(final List<Byte> bytes,
            final LengthConstraint lengthConstraint, final String units) {
        super();
        this.bytes = bytes;
        this.lengthConstraint = lengthConstraint;
        this.units = units;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.TypeDefinition#getBaseType()
     */
    @Override
    public BinaryTypeDefinition getBaseType() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.TypeDefinition#getUnits()
     */
    @Override
    public String getUnits() {
        return units;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.TypeDefinition#getDefaultValue()
     */
    @Override
    public Object getDefaultValue() {
        return bytes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.SchemaNode#getQName()
     */
    @Override
    public QName getQName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.SchemaNode#getPath()
     */
    @Override
    public SchemaPath getPath() {
        return path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.SchemaNode#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.SchemaNode#getReference()
     */
    @Override
    public String getReference() {
        return reference;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.controller.yang.model.api.SchemaNode#getStatus()
     */
    @Override
    public Status getStatus() {
        return Status.CURRENT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.opendaylight.controller.yang.model.base.type.api.BinaryTypeDefinition#getLengthConstraint
     * ()
     */
    @Override
    public LengthConstraint getLengthConstraint() {
        return lengthConstraint;
    }

    @Override
    public List<ExtensionDefinition> getExtensionSchemaNodes() {
        return Collections.emptyList();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bytes == null) ? 0 : bytes.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime
                * result
                + ((lengthConstraint == null) ? 0 : lengthConstraint.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result
                + ((reference == null) ? 0 : reference.hashCode());
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BinaryType other = (BinaryType) obj;
        if (bytes == null) {
            if (other.bytes != null) {
                return false;
            }
        } else if (!bytes.equals(other.bytes)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (lengthConstraint == null) {
            if (other.lengthConstraint != null) {
                return false;
            }
        } else if (!lengthConstraint.equals(other.lengthConstraint)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        if (reference == null) {
            if (other.reference != null) {
                return false;
            }
        } else if (!reference.equals(other.reference)) {
            return false;
        }
        if (units == null) {
            if (other.units != null) {
                return false;
            }
        } else if (!units.equals(other.units)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BinaryType [name=");
        builder.append(name);
        builder.append(", path=");
        builder.append(path);
        builder.append(", description=");
        builder.append(description);
        builder.append(", reference=");
        builder.append(reference);
        builder.append(", bytes=");
        builder.append(bytes);
        builder.append(", lengthConstraint=");
        builder.append(lengthConstraint);
        builder.append(", units=");
        builder.append(units);
        builder.append("]");
        return builder.toString();
    }
}
