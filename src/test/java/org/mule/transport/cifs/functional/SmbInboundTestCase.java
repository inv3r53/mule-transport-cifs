/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cifs.functional;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.transport.cifs.util.SmbUtil;
import org.mule.transport.file.FileConnector;

public class SmbInboundTestCase extends AbstractSmbTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "smb-inbound-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception
    {
        putFile();
        super.doSetUp();
    }

    private void putFile() throws Exception
    {
        new SmbUtil().createFile("input.txt", TEST_MESSAGE);
    }

    public void testMessageWasReceivedFromSmbServer() throws MuleException
    {
        MuleMessage result = muleContext.getClient().request("vm://data", RECEIVE_TIMEOUT);
        assertNotNull(result);
        assertEquals(TEST_MESSAGE.getBytes(), (byte[]) result.getPayload());
        assertEquals("input.txt", result.getOutboundProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME));
    }
}
