/* Copyright (c) 2000-2003, jMock.org. See LICENSE.txt */
package test.jmock.core.stub;

import junit.framework.TestCase;
import junit.framework.AssertionFailedError;

import org.jmock.core.Invocation;
import org.jmock.core.stub.ReturnStub;
import org.jmock.expectation.AssertMo;

public class ReturnStubTest 
	extends TestCase 
{
	static final String RESULT = "result";

	Object invokedObject;
	Class invokedObjectClass;
    Invocation invocation;
    ReturnStub returnStub; 
    
    public void setUp() {
	    invokedObject = "INVOKED-OBJECT";
	    invokedObjectClass = Void.class;

	    returnStub  = new ReturnStub(RESULT);
    }
    
    public void testReturnsValuePassedToConstructor() throws Throwable {
	    invocation = new Invocation(
	        invokedObject,
            invokedObjectClass, "ignoredMethodNameName", new Class[0], RESULT.getClass(),
            new Object[0] );

        assertSame( "Should be the same result object",
        		    RESULT, returnStub.invoke(invocation) );
    }
    
    public void testIncludesValueInDescription() {
    	StringBuffer buffer = new StringBuffer();
    	
    	returnStub.describeTo(buffer);
    	
    	String description = buffer.toString();
    	
    	assertTrue( "contains result in description",
    				description.indexOf(RESULT.toString()) >= 0 );
    	assertTrue( "contains 'returns' in description",
    				description.indexOf("returns") >= 0 );
    }
	
	public void testThrowsAssertionFailedErrorIfTriesToReturnValueOfIncompatibleType()
		throws Throwable
	{
		invocation = new Invocation(
		    invokedObject,
	        invokedObjectClass, "ignoredMethodNameName", new Class[0], int.class,
	        new Object[0] );

	    try {
		    returnStub.invoke(invocation);
	    }
		catch( AssertionFailedError ex ) {
		    AssertMo.assertIncludes( "expected return type", invocation.getReturnType().toString(), ex.getMessage() );
		    AssertMo.assertIncludes( "returned value type", RESULT.getClass().toString(), ex.getMessage() );
		    return;
	    }
		fail("should have failed");
	}

	public void testCanReturnNullReference()
		throws Throwable
	{
		invocation = new Invocation(
		    invokedObject,
	        invokedObjectClass, "ignoredMethodNameName", new Class[0], String.class,
	        new Object[0] );

		returnStub = new ReturnStub(null);

		assertNull( "should return null", returnStub.invoke(invocation) );
	}

	public void testThrowsAssertionFailedErrorIfTriesToReturnNullFromMethodWithPrimitiveReturnType()
		throws Throwable
	{
		invocation = new Invocation(
		    invokedObject,
	        invokedObjectClass, "ignoredMethodNameName", new Class[0], int.class,
	        new Object[0] );

		returnStub = new ReturnStub(null);

		try {
			returnStub.invoke(invocation);
		}
		catch( AssertionFailedError ex ) {
			AssertMo.assertIncludes( "expected return type", invocation.getReturnType().toString(), ex.getMessage() );
			AssertMo.assertIncludes( "null", String.valueOf((Object)null), ex.getMessage() );
			return;
		}
		fail("should have failed");
	}
}
